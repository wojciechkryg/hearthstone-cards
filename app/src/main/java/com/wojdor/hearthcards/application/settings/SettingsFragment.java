package com.wojdor.hearthcards.application.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.wojdor.hearthcards.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_preferences);
    }
}
