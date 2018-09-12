package com.wojdor.hearthstonecards.application.splash

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel
import com.wojdor.hearthstonecards.data.service.mapper.VersionInfoMapper
import com.wojdor.hearthstonecards.domain.VersionInfo

import io.reactivex.schedulers.Schedulers

class SplashViewModel(application: Application) : BaseAndroidViewModel(application) {

    val localVersionInfo
        get() = MutableLiveData<VersionInfo>().apply { value = userSession.versionInfo }

    val remoteVersionInfo: LiveData<VersionInfo>
        get() {
            val data = MutableLiveData<VersionInfo>()
            disposable.add(cardApi.versionInfo
                    .subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.io())
                    .subscribe({ data.postValue(VersionInfoMapper.map(it)) },
                            { data.postValue(null) }))
            return data
        }

    val locale: LiveData<String>
        get() = MutableLiveData<String>().apply { value = userSession.locale }

    fun setLocale(locale: String) {
        userSession.locale = locale
    }

    val wasLanguageChanged: LiveData<Boolean>
        get() = MutableLiveData<Boolean>().apply { value = userSession.wasLanguageChanged() }

    fun wasLanguageChanged(wasLanguageChanged: Boolean) {
        userSession.wasLanguageChanged(wasLanguageChanged)
    }
}
