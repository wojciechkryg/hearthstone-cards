package com.wojdor.hearthstonecards.application.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.base.BaseActivity
import com.wojdor.hearthstonecards.application.classpager.ClassPagerActivity
import com.wojdor.hearthstonecards.application.extension.observe
import com.wojdor.hearthstonecards.application.update.UpdateIntentService
import com.wojdor.hearthstonecards.application.update.UpdateResultReceiver
import com.wojdor.hearthstonecards.application.util.Language
import com.wojdor.hearthstonecards.domain.VersionInfo
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.toast

class SplashActivity : BaseActivity<SplashViewModel>(), UpdateResultReceiver.Receiver {

    override val viewModelClass = SplashViewModel::class.java

    private val updateResultReceiver by lazy { UpdateResultReceiver(Handler()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashLoadingAv.playAnimation()
        setupLocale()
        checkUpdate()
    }

    private fun setupLocale() {
        viewModel.locale.observe(this) { checkLocale(it) }
    }

    private fun checkLocale(locale: String?) {
        if (locale == null) {
            setupDefaultLocale()
        }
    }

    private fun setupDefaultLocale() {
        val currentLanguage = Language().currentLanguage
        val supportedLanguages = resources.getStringArray(R.array.localeValues)
        if (supportedLanguages.contains(currentLanguage)) {
            viewModel.setLocale(currentLanguage)
        } else {
            viewModel.setLocale(getString(R.string.settings_preferences_default_locale))
        }
    }

    private fun checkUpdate() {
        splashLoadingInfoTv.setText(R.string.check_update_info)
        viewModel.remoteVersionInfo.observe(this) { remoteVersionInfo ->
            viewModel.localVersionInfo.observe(this) { localVersionInfo ->
                viewModel.wasLanguageChanged.observe(this) { wasLanguageChanged ->
                    checkVersions(remoteVersionInfo, localVersionInfo, wasLanguageChanged)
                }
            }
        }
    }

    private fun checkVersions(remoteVersionInfo: VersionInfo?, localVersionInfo: VersionInfo?,
                              wasLanguageChanged: Boolean?) {
        when {
            shouldCloseApp(remoteVersionInfo, localVersionInfo, wasLanguageChanged) -> closeAppWithError()
            shouldUpdate(localVersionInfo, remoteVersionInfo, wasLanguageChanged) -> startUpdate(remoteVersionInfo)
            else -> launchClassPagerActivity()
        }
    }

    private fun shouldCloseApp(localVersionInfo: VersionInfo?, remoteVersionInfo: VersionInfo?, wasLanguageChanged: Boolean?) =
            isWrongData(localVersionInfo, remoteVersionInfo)
                    || wasLanguageChangedWithoutInternet(remoteVersionInfo, wasLanguageChanged)

    private fun isWrongData(localVersionInfo: VersionInfo?, remoteVersionInfo: VersionInfo?): Boolean {
        return localVersionInfo == null && remoteVersionInfo == null
    }

    private fun wasLanguageChangedWithoutInternet(remoteVersionInfo: VersionInfo?, wasLanguageChanged: Boolean?): Boolean {
        return remoteVersionInfo == null && wasLanguageChanged == true
    }

    private fun shouldUpdate(localVersionInfo: VersionInfo?, remoteVersionInfo: VersionInfo?, wasLanguageChanged: Boolean?) =
            isFirstLaunch(localVersionInfo, remoteVersionInfo)
                    || isNewVersionOnRemote(remoteVersionInfo, localVersionInfo)
                    || wasLanguageChanged(remoteVersionInfo, wasLanguageChanged)

    private fun isFirstLaunch(localVersionInfo: VersionInfo?, remoteVersionInfo: VersionInfo?): Boolean {
        return localVersionInfo == null && remoteVersionInfo != null
    }

    private fun closeAppWithError() {
        toast(R.string.no_internet)
        finish()
    }

    private fun isNewVersionOnRemote(remoteVersionInfo: VersionInfo?, localVersionInfo: VersionInfo?): Boolean {
        return remoteVersionInfo != null && localVersionInfo?.notEquals(remoteVersionInfo) == true
    }

    private fun wasLanguageChanged(remoteVersionInfo: VersionInfo?, wasLanguageChanged: Boolean?): Boolean {
        return remoteVersionInfo != null && wasLanguageChanged!!
    }

    private fun startUpdate(remoteVersionInfo: VersionInfo?) {
        splashLoadingInfoTv.setText(R.string.download_data_info)
        UpdateIntentService.update(this, remoteVersionInfo, updateResultReceiver)
    }

    private fun launchClassPagerActivity() {
        val intent = Intent(this, ClassPagerActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        updateResultReceiver.setReceiver(this)
    }

    override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
        if (resultCode == UpdateResultReceiver.RESULT_SUCCESS) {
            viewModel.wasLanguageChanged(false)
            launchClassPagerActivity()
        } else {
            closeAppWithError()
        }
    }

    override fun onPause() {
        super.onPause()
        updateResultReceiver.setReceiver(null)
    }
}
