package com.wojdor.hearthstonecards.application.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.squareup.picasso.Picasso
import com.wojdor.hearthstonecards.R
import org.koin.android.viewmodel.ext.android.sharedViewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by sharedViewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings_preferences)
        findPreference(getString(R.string.settings_preferences_locale_key)).setOnPreferenceChangeListener { _, _ ->
            restartApp()
            true
        }
    }

    private fun restartApp() {
        clearData()
        relaunchApp()
    }

    private fun clearData() {
        clearCache()
        viewModel.languageChanged()
    }

    private fun clearCache() {
        //TODO: Invalidate cache
    }

    private fun relaunchApp() {
        context?.let {
            val intent = it.packageManager.getLaunchIntentForPackage(it.packageName)?.apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }
}
