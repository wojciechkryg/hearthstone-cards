package com.wojdor.hearthstonecards.application.splash

import android.app.Application
import android.arch.lifecycle.LiveData
import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel
import com.wojdor.hearthstonecards.domain.VersionInfo

class SplashViewModel(application: Application) : BaseAndroidViewModel(application) {

    val localVersionInfo: LiveData<VersionInfo>
        get() = repository.localVersionInfo

    val remoteVersionInfo: LiveData<VersionInfo>
        get() = repository.remoteVersionInfo

    val locale: LiveData<String>
        get() = repository.locale

    fun setLocale(locale: String) {
        repository.setLocale(locale)
    }

    val wasLanguageChanged: LiveData<Boolean>
        get() = repository.wasLanguageChanged

    fun wasLanguageChanged(wasLanguageChanged: Boolean) {
        repository.wasLanguageChanged(wasLanguageChanged)
    }
}
