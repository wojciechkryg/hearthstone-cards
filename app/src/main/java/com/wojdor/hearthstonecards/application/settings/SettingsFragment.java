package com.wojdor.hearthstonecards.application.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.bumptech.glide.Glide;
import com.wojdor.hearthstonecards.R;
import com.wojdor.hearthstonecards.data.session.UserSession;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);
        findPreference(getString(R.string.settings_preferences_locale_key)).setOnPreferenceChangeListener((preference, newValue) -> {
            restartApp();
            return true;
        });
    }

    private void restartApp() {
        clearCache();
        UserSession.getInstance(getContext()).wasLanguageChanged(true);
        Intent intent = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void clearCache() {
        new Thread(() -> Glide.get(getContext()).clearDiskCache()).start();
        Glide.get(getContext()).clearMemory();
    }
}
