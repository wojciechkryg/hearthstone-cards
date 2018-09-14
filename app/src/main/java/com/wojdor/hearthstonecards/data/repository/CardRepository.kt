package com.wojdor.hearthstonecards.data.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.wojdor.hearthstonecards.application.extension.isPositive
import com.wojdor.hearthstonecards.data.database.dao.CardDao
import com.wojdor.hearthstonecards.data.service.CardApi
import com.wojdor.hearthstonecards.data.service.mapper.VersionInfoMapper
import com.wojdor.hearthstonecards.data.session.UserSession
import com.wojdor.hearthstonecards.domain.Card
import com.wojdor.hearthstonecards.domain.VersionInfo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CardRepository(private val cardApi: CardApi,
                     private val cardDao: CardDao,
                     private val userSession: UserSession) {

    private val disposables by lazy { CompositeDisposable() }

    val remoteVersionInfo: LiveData<VersionInfo>
        get() {
            val data = MutableLiveData<VersionInfo>()
            disposables.add(cardApi.getVersionInfo()
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ data.postValue(VersionInfoMapper.map(it)) },
                            { data.postValue(null) }))
            return data
        }

    val localVersionInfo
        get() = MutableLiveData<VersionInfo>().apply { value = userSession.versionInfo }

    val classesWhichHaveCards: LiveData<List<String>>
        get() {
            val data = MutableLiveData<List<String>>()
            Observable.fromCallable {
                val classNames = userSession.versionInfo?.classNames ?: emptyList()
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

    fun onDestroy() {
        disposables.clear()
    }
}