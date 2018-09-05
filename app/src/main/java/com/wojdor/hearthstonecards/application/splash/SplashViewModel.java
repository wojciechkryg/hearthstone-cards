package com.wojdor.hearthstonecards.application.splash;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthstonecards.data.service.mapper.VersionInfoMapper;
import com.wojdor.hearthstonecards.domain.VersionInfo;

import io.reactivex.schedulers.Schedulers;

public class SplashViewModel extends BaseAndroidViewModel {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<VersionInfo> getLocalVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        data.setValue(userSession.getVersionInfo());
        return data;
    }

    public LiveData<VersionInfo> getRemoteVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        disposable.add(cardApi.getVersionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(versionInfoModel -> data.postValue(VersionInfoMapper.map(versionInfoModel)),
                        error -> data.postValue(null)));
        return data;
    }

    public LiveData<String> getLocale() {
        MutableLiveData<String> data = new MutableLiveData<>();
        data.setValue(userSession.getLocale());
        return data;
    }

    public void setLocale(String locale) {
        userSession.setLocale(locale);
    }

    public LiveData<Boolean> wasLanguageChanged() {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        data.setValue(userSession.wasLanguageChanged());
        return data;
    }

    public void wasLanguageChanged(boolean wasLanguageChanged) {
        userSession.wasLanguageChanged(wasLanguageChanged);
    }
}
