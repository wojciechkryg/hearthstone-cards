package com.wojdor.hearthstonecards.application.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.wojdor.hearthstonecards.R;
import com.wojdor.hearthstonecards.application.base.BaseActivity;
import com.wojdor.hearthstonecards.application.classpager.ClassPagerActivity;
import com.wojdor.hearthstonecards.application.update.UpdateIntentService;
import com.wojdor.hearthstonecards.application.update.UpdateResultReceiver;
import com.wojdor.hearthstonecards.application.util.Language;
import com.wojdor.hearthstonecards.domain.VersionInfo;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements UpdateResultReceiver.Receiver {

    @BindView(R.id.splashLoadingAv)
    LottieAnimationView splashLoadingAv;
    @BindView(R.id.splashLoadingInfoTv)
    TextView splashLoadingInfoTv;

    private SplashViewModel viewModel;
    private UpdateResultReceiver updateResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        updateResultReceiver = new UpdateResultReceiver(new Handler());
        splashLoadingAv.playAnimation();
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
        splashLoadingInfoTv.setText(R.string.check_update_info);
        viewModel.getRemoteVersionInfo().observe(this, remoteVersionInfo ->
                viewModel.getLocalVersionInfo().observe(this, localVersionInfo ->
                        viewModel.wasLanguageChanged().observe(this, wasLanguageChanged ->
                                checkVersions(remoteVersionInfo, localVersionInfo, wasLanguageChanged))));
    }

    private void checkVersions(VersionInfo remoteVersionInfo, VersionInfo localVersionInfo,
                               Boolean wasLanguageChanged) {
        if (isWrongData(remoteVersionInfo, localVersionInfo)
                || wasLanguageChangedWithoutInternet(remoteVersionInfo, wasLanguageChanged)) {
            closeAppWithError();
        } else if (isFirstLaunch(localVersionInfo, remoteVersionInfo)
                || isNewVersionOnRemote(remoteVersionInfo, localVersionInfo)
                || wasLanguageChanged(remoteVersionInfo, wasLanguageChanged)) {
            startUpdate(remoteVersionInfo);
        } else {
            launchClassPagerActivity();
        }
    }

    private boolean isFirstLaunch(VersionInfo localVersionInfo, VersionInfo remoteVersionInfo) {
        return localVersionInfo == null && remoteVersionInfo != null;
    }

    private boolean isWrongData(VersionInfo remoteVersionInfo, VersionInfo localVersionInfo) {
        return remoteVersionInfo == null && localVersionInfo == null;
    }

    private void closeAppWithError() {
        Toast.makeText(this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean isNewVersionOnRemote(VersionInfo remoteVersionInfo, VersionInfo localVersionInfo) {
        return remoteVersionInfo != null && localVersionInfo.notEquals(remoteVersionInfo);
    }

    private boolean wasLanguageChangedWithoutInternet(VersionInfo remoteVersionInfo, Boolean wasLanguageChanged) {
        return remoteVersionInfo == null && wasLanguageChanged;
    }

    private boolean wasLanguageChanged(VersionInfo remoteVersionInfo, Boolean wasLanguageChanged) {
        return remoteVersionInfo != null && wasLanguageChanged;
    }

    private void startUpdate(VersionInfo remoteVersionInfo) {
        splashLoadingInfoTv.setText(R.string.download_data_info);
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
            viewModel.wasLanguageChanged(false);
            launchClassPagerActivity();
        } else {
            closeAppWithError();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        updateResultReceiver.setReceiver(null);
    }
}
