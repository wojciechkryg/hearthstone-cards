package com.wojdor.hearthcards.application.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
