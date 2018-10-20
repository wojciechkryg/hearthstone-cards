package com.wojdor.hearthstonecards.application.splash

import android.content.Intent
import android.os.Bundle
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.base.BaseActivity
import com.wojdor.hearthstonecards.application.classpager.ClassPagerActivity
import com.wojdor.hearthstonecards.application.extension.observe
import com.wojdor.hearthstonecards.application.extension.observeNonNull
import com.wojdor.hearthstonecards.application.util.Language
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.toast
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<SplashViewModel>() {

    override val viewModel: SplashViewModel by viewModel()

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
        // TODO: Shouldn't check it in view model?
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
        viewModel.isNewVersionAvailable.observeNonNull(this) {
            // TODO: Add state when no internet connection and no data downloaded (Enum or custom Result/State class for api call)
            if (it) {
                startUpdate()
            } else {
                launchClassPagerActivity()
            }
        }
    }

    private fun closeAppWithError() {
        toast(R.string.no_internet)
        finish()
    }

    private fun startUpdate() {
        splashLoadingInfoTv.setText(R.string.download_data_info)
        viewModel.downloadCardData()
    }

    private fun launchClassPagerActivity() {
        val intent = Intent(this, ClassPagerActivity::class.java)
        startActivity(intent)
        finish()
    }
}
