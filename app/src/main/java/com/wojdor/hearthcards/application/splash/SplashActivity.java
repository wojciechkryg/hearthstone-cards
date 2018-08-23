package com.wojdor.hearthcards.application.splash;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.classpager.ClassPagerActivity;
import com.wojdor.hearthcards.application.update.UpdateIntentService;
import com.wojdor.hearthcards.application.update.UpdateResultReceiver;
import com.wojdor.hearthcards.domain.VersionInfo;

public class SplashActivity extends BaseActivity implements UpdateResultReceiver.Receiver {

    private SplashViewModel viewModel;
    private UpdateResultReceiver updateResultReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
        updateResultReceiver = new UpdateResultReceiver(new Handler());
        checkUpdate();
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
        // TODO: Show error dialog
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
