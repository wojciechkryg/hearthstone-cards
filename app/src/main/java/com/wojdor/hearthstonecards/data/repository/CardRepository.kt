package com.wojdor.hearthstonecards.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.application.extension.isPositive
import com.wojdor.hearthstonecards.application.util.CardImageDownloader
import com.wojdor.hearthstonecards.data.database.CardDao
import com.wojdor.hearthstonecards.data.service.CardApi
import com.wojdor.hearthstonecards.data.service.mapper.CardMapper
import com.wojdor.hearthstonecards.data.service.mapper.VersionInfoMapper
import com.wojdor.hearthstonecards.data.service.mapper.ZipResultMapper
import com.wojdor.hearthstonecards.data.service.model.CardModel
import com.wojdor.hearthstonecards.data.session.UserSession
import com.wojdor.hearthstonecards.domain.Card
import com.wojdor.hearthstonecards.domain.VersionInfo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CardRepository private constructor(private val cardApi: CardApi,
                                         private val cardDao: CardDao,
                                         private val userSession: UserSession,
                                         private val cardImageDownloader: CardImageDownloader) {

    private val disposables by lazy { CompositeDisposable() }

    val remoteVersionInfo: LiveData<VersionInfo>
        get() {
            val data = MutableLiveData<VersionInfo>()
            disposables.add(cardApi.getVersionInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ data.postValue(VersionInfoMapper.map(it)) },
                            { data.postValue(VersionInfo()) }))
            return data
        }

    val localVersionInfo by lazy {
        MutableLiveData<VersionInfo>().apply { postValue(userSession.versionInfo) }
    }

    private fun setLocalVersionInfo(versionInfo: VersionInfo) {
        userSession.versionInfo = versionInfo
        localVersionInfo.postValue(versionInfo)
    }

    val classesWhichHaveCards: LiveData<List<String>>
        get() {
            val data = MutableLiveData<List<String>>()
            Observable.fromCallable {
                val classNames = localVersionInfo.value?.classNames ?: emptyList()
                classNames.filter { cardDao.getAmountOfCardsFromClass(it).isPositive }
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data.setValue(it) }
            return data
        }

    val locale: LiveData<String>
        get() = MutableLiveData<String>().apply { value = userSession.locale }

    val wasLanguageChanged: LiveData<Boolean>
        get() = MutableLiveData<Boolean>().apply { value = userSession.wasLanguageChanged }

    fun setLocale(locale: String) {
        userSession.locale = locale
    }

    fun wasLanguageChanged(wasLanguageChanged: Boolean) {
        userSession.wasLanguageChanged = wasLanguageChanged
    }

    fun getCardByCardId(cardId: String): LiveData<Card> = cardDao.getCardByCardId(cardId)

    fun getCardsFromClass(className: String): LiveData<List<Card>> = cardDao.getCardsFromClass(className)

    fun downloadCardData(versionInfo: VersionInfo, locale: String) {
        val apiCallsForAllClasses = getApiCallsForAllClasses(versionInfo, locale)
        downloadCardsForAllClasses(apiCallsForAllClasses, versionInfo, locale)
    }

    private fun getApiCallsForAllClasses(versionInfo: VersionInfo, locale: String): MutableList<Single<List<CardModel>>> {
        return mutableListOf<Single<List<CardModel>>>().apply {
            versionInfo.classNames.forEach { this.add(downloadDataForClass(it, locale)) }
        }
    }

    private fun downloadCardsForAllClasses(apiCalls: List<Single<List<CardModel>>>, versionInfo: VersionInfo, locale: String) {
        Single.zip<List<CardModel>, List<CardModel>>(apiCalls) { ZipResultMapper.map(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ onSuccess(it, versionInfo, locale) },
                        { Timber.e(it) })
    }

    private fun downloadDataForClass(className: String, locale: String): Single<List<CardModel>> {
        return cardApi.getCollectibleCardsForClass(className, locale)
                .onErrorReturnItem(emptyList())
    }

    private fun onSuccess(cardModels: List<CardModel>, versionInfo: VersionInfo, locale: String) {
        cardModels.forEach { cardImageDownloader.getImage(it.cardId, locale) }
        val cards = CardMapper.map(cardModels)
        cardDao.insertCards(cards)
        setLocalVersionInfo(versionInfo)
    }

    fun onDestroy() {
        disposables.clear()
    }

    companion object {
        private var instance: CardRepository? = null

        fun getInstance(cardApi: CardApi, cardDao: CardDao, userSession: UserSession,
                        cardImageDownloader: CardImageDownloader): CardRepository {
            if (instance == null) {
                instance = CardRepository(cardApi, cardDao, userSession, cardImageDownloader)
            }
            return instance as CardRepository
        }
    }
}
