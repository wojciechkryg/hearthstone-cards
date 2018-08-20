package com.wojdor.hearthcards.application.classpager;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.domain.VersionInfo;

public class ClassPagerActivity extends BaseActivity {

    private ClassPagerViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_pager);
        viewModel = ViewModelProviders.of(this).get(ClassPagerViewModel.class);
        viewModel.getLocalVersionInfo().observe(this, this::checkLocalVersionInfo);
    }

    private void checkLocalVersionInfo(VersionInfo localVersionInfo) {
        if (localVersionInfo == null) {
             // TODO: start Intent Service and download data first time
        } else {
            viewModel.getRemoteVersionInfo().observe(this, remoteVersionInfo ->
                    checkRemoteVersionInfo(localVersionInfo, remoteVersionInfo));
        }
    }

    private void checkRemoteVersionInfo(VersionInfo localVersionInfo, VersionInfo remoteVersionInfo) {
        if (localVersionInfo.equals(remoteVersionInfo)) return;
        viewModel.downloadNewData(remoteVersionInfo);
    }
}
