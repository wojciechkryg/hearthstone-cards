package com.wojdor.hearthstonecards.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.app.extension.isPositive
import com.wojdor.hearthstonecards.app.util.CardImageDownloader
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
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

class CardRepository(private val cardApi: CardApi,
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
            disposables.add(Observable.fromCallable {
                val classNames = localVersionInfo.value?.classNames ?: emptyList()
                classNames.filter { cardDao.getAmountOfCardsFromClass(it).isPositive }
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { data.setValue(it) })
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

    fun getAllCards(): List<Card> = cardDao.getAllCards()

    fun downloadCardData(versionInfo: VersionInfo, locale: String) {
        val apiCallsForCardsFromEachClass = getApiCallsForCardsFromEachClass(versionInfo, locale)
        downloadCardsFromEachClass(apiCallsForCardsFromEachClass, versionInfo, locale)
    }

    private fun getApiCallsForCardsFromEachClass(versionInfo: VersionInfo, locale: String): MutableList<Single<List<CardModel>>> {
        return mutableListOf<Single<List<CardModel>>>().apply {
            versionInfo.classNames.forEach { this.add(downloadDataForClass(it, locale)) }
        }
    }

    private fun downloadDataForClass(className: String, locale: String): Single<List<CardModel>> {
        return cardApi.getCollectibleCardsForClass(className, locale)
                .onErrorReturnItem(emptyList())
    }

    private fun downloadCardsFromEachClass(apiCalls: List<Single<List<CardModel>>>, versionInfo: VersionInfo, locale: String) {
        disposables.add(Single.zip<List<CardModel>, List<CardModel>>(apiCalls) { ZipResultMapper.mapToCardModels(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({ downloadImagesForCards(it, versionInfo, locale) }, Timber::e))
    }

    private fun downloadImagesForCards(cardModels: List<CardModel>, versionInfo: VersionInfo, locale: String) {
        val time = System.currentTimeMillis()
        val threadCount = Runtime.getRuntime().availableProcessors()
        val threadPoolExecutor = Executors.newFixedThreadPool(threadCount)
        val scheduler = Schedulers.from(threadPoolExecutor)
        val observableCardModels = cardModels.map { Observable.just(it) }
        val groupIndex = AtomicInteger()
        disposables.add(Observable.merge(observableCardModels)
                .groupBy { groupIndex.getAndIncrement() % threadCount }
                .flatMap {
                    it.observeOn(scheduler).map {
                        cardImageDownloader.getImage(it.cardId, locale)
                    }
                }
                .subscribeOn(Schedulers.computation())
                .subscribe({}, Timber::e, {
                    Timber.d("${(System.currentTimeMillis() - time) / 1000}")
                    val cards = CardMapper.map(cardModels)
                    cardDao.insertCards(cards)
                    setLocalVersionInfo(versionInfo)
                }))
    }

    fun onDestroy() {
        disposables.clear()
    }
}
