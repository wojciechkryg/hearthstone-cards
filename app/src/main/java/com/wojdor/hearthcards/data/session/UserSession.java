package com.wojdor.hearthcards.data.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.util.JsonParser;
import com.wojdor.hearthcards.domain.VersionInfo;

public final class UserSession {

    private static final String VERSION_INFO_KEY = "VERSION_INFO_KEY";

    private static UserSession instance;

    private final String localeKey;

    private SharedPreferences sharedPreferences;
    private JsonParser jsonParser;

    private UserSession(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        jsonParser = new JsonParser();
        localeKey = context.getString(R.string.settings_preferences_locale_key);
    }

    public static UserSession getInstance(Context context) {
        if (instance == null) {
            instance = new UserSession(context);
        }
        return instance;
    }

    public VersionInfo getVersionInfo() {
        String versionInfoJson = sharedPreferences.getString(VERSION_INFO_KEY, null);
        return jsonParser.fromJson(VersionInfo.class, versionInfoJson);
    }

    public void setVersionInfo(VersionInfo versionInfo) {
        String versionInfoJson = jsonParser.toJson(versionInfo);
        sharedPreferences.edit().putString(VERSION_INFO_KEY, versionInfoJson).apply();
    }

    public String getLocale() {
        return sharedPreferences.getString(localeKey, null);
    }

    public void setLocale(String locale) {
        sharedPreferences.edit().putString(localeKey, locale).apply();
    }
}
