package com.wojdor.hearthstonecards.application.settings

import android.content.Intent
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.bumptech.glide.Glide
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.data.session.UserSession

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle, rootKey: String) {
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
        context?.let { UserSession.getInstance(it).wasLanguageChanged = true }
    }

    private fun clearCache() {
        context?.let {
            Thread { Glide.get(it).clearDiskCache() }.start()
            Glide.get(it).clearMemory()
        }
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
