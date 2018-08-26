package com.wojdor.hearthcards.application.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.classpager.ClassPagerActivity;
import com.wojdor.hearthcards.application.update.UpdateIntentService;
import com.wojdor.hearthcards.application.update.UpdateResultReceiver;
import com.wojdor.hearthcards.application.util.Language;
import com.wojdor.hearthcards.domain.VersionInfo;

import java.util.Arrays;
import java.util.List;

public class SplashActivity extends BaseActivity implements UpdateResultReceiver.Receiver {

    private SplashViewModel viewModel;
    private UpdateResultReceiver updateResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        updateResultReceiver = new UpdateResultReceiver(new Handler());
        setupLocale();
        checkUpdate();
    }

    private void setupLocale() {
        viewModel.getLocale().observe(this, this::checkLocale);
    }

    private void checkLocale(String locale) {
        if (locale == null) {
            setupDefaultLocale();
        }
    }

    private void setupDefaultLocale() {
        String currentLanguage = new Language().getCurrentLanguage();
        String[] supportedLanguages = getResources().getStringArray(R.array.localeValues);
        List<String> supportedLanguagesList = Arrays.asList(supportedLanguages);
        if (supportedLanguagesList.contains(currentLanguage)) {
            viewModel.setLocale(currentLanguage);
        } else {
            viewModel.setLocale(getString(R.string.settings_preferences_default_locale));
        }
    }

    private void checkUpdate() {
        viewModel.getRemoteVersionInfo().observe(this, remoteVersionInfo ->
                viewModel.getLocalVersionInfo().observe(this, localVersionInfo ->
                        checkVersions(remoteVersionInfo, localVersionInfo)));
    }

    private void checkVersions(VersionInfo remoteVersionInfo, VersionInfo localVersionInfo) {
        if (remoteVersionInfo == null && localVersionInfo == null) {
            showError();
        } else if (localVersionInfo == null || isNewVersionOnRemote(remoteVersionInfo, localVersionInfo)) {
            startUpdate(remoteVersionInfo);
        } else {
            launchClassPagerActivity();
        }
    }

    private void showError() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isNewVersionOnRemote(VersionInfo remoteVersionInfo, VersionInfo localVersionInfo) {
        return remoteVersionInfo != null && localVersionInfo.notEquals(remoteVersionInfo);
    }

    private void startUpdate(VersionInfo remoteVersionInfo) {
        UpdateIntentService.update(this, remoteVersionInfo, updateResultReceiver);
    }

    private void launchClassPagerActivity() {
        Intent intent = new Intent(this, ClassPagerActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateResultReceiver.setReceiver(this);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        if (resultCode == UpdateResultReceiver.RESULT_SUCCESS) {
            launchClassPagerActivity();
        } else {
            showError();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateResultReceiver.setReceiver(null);
    }
}
