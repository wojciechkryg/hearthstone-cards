package com.wojdor.hearthstonecards.data.session

import android.content.Context
import android.preference.PreferenceManager
import com.wojdor.hearthstonecards.R
import com.wojdor.hearthstonecards.application.util.JsonParser
import com.wojdor.hearthstonecards.domain.VersionInfo

class UserSession private constructor(context: Context) {

    private val localeKey by lazy { context.getString(R.string.settings_preferences_locale_key) }
    private val sharedPreferences by lazy { PreferenceManager.getDefaultSharedPreferences(context) }
    private val jsonParser by lazy { JsonParser() }

    var versionInfo: VersionInfo?
        get() {
            val versionInfoJson = sharedPreferences.getString(VERSION_INFO_KEY, null) ?: return null
            return jsonParser.fromJson(VersionInfo::class.java, versionInfoJson)
        }
        set(versionInfo) {
            val versionInfoJson = jsonParser.toJson(versionInfo)
            sharedPreferences.edit().putString(VERSION_INFO_KEY, versionInfoJson).apply()
        }

    var locale: String?
        get() = sharedPreferences.getString(localeKey, null)
        set(locale) = sharedPreferences.edit().putString(localeKey, locale).apply()


    var wasLanguageChanged: Boolean
        get() = sharedPreferences.getBoolean(WAS_LANGUAGE_CHANGED_KEY, false)
        set(wasLanguageChanged) = sharedPreferences.edit().putBoolean(WAS_LANGUAGE_CHANGED_KEY, wasLanguageChanged).apply()

    companion object {
        private const val VERSION_INFO_KEY = "VERSION_INFO_KEY"
        private const val WAS_LANGUAGE_CHANGED_KEY = "WAS_LANGUAGE_CHANGED_KEY"

        private var instance: UserSession? = null

        fun getInstance(context: Context): UserSession {
            if (instance == null) {
                instance = UserSession(context)
            }
            return instance as UserSession
        }
    }
}
