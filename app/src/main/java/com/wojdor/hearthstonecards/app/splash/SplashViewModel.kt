package com.wojdor.hearthstonecards.app.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wojdor.hearthstonecards.app.base.BaseViewModel
import com.wojdor.hearthstonecards.app.extension.notEquals
import com.wojdor.hearthstonecards.app.extension.setDifferentValue
import com.wojdor.hearthstonecards.data.repository.CardRepository
import com.wojdor.hearthstonecards.domain.VersionInfo

class SplashViewModel(cardRepository: CardRepository) : BaseViewModel(cardRepository) {

    private val localVersionInfo = repository.localVersionInfo

    private val remoteVersionInfo = repository.remoteVersionInfo

    val locale: LiveData<String>
        get() = repository.locale

    fun setLocale(locale: String) {
        repository.setLocale(locale)
    }

    val isNewVersionAvailable: LiveData<Boolean> by lazy {
        MediatorLiveData<Boolean>().apply {
            addSource(remoteVersionInfo) { setDifferentValue(isNewVersion(localVersionInfo.value, it)) }
            addSource(localVersionInfo) { setDifferentValue(isNewVersion(it, remoteVersionInfo.value)) }
        }
    }

    fun downloadCardData() {
        // TODO: These 'returns' can aim to incorrect ui experience
        repository.downloadCardData(remoteVersionInfo.value ?: return, locale.value ?: return)
    }

    private fun isNewVersion(localVersionInfo: VersionInfo?, remoteVersionInfo: VersionInfo?): Boolean? {
        return if (remoteVersionInfo == null) {
            null
        } else {
            isFirstLaunch(localVersionInfo) || isNewVersionOnRemote(remoteVersionInfo, localVersionInfo)
        }
    }

    private fun isNewVersionOnRemote(remoteVersionInfo: VersionInfo?, localVersionInfo: VersionInfo?) =
            remoteVersionInfo != null && remoteVersionInfo.version.isNotEmpty() && localVersionInfo?.notEquals(remoteVersionInfo) == true

    private fun isFirstLaunch(localVersionInfo: VersionInfo?) = localVersionInfo == null
}
