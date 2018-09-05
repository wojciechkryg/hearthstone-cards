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
        data.setValue(getUserSession().getVersionInfo());
        return data;
    }

    public LiveData<VersionInfo> getRemoteVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        getDisposable().add(getCardApi().getVersionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(versionInfoModel -> data.postValue(VersionInfoMapper.map(versionInfoModel)),
                        error -> data.postValue(null)));
        return data;
    }

    public LiveData<String> getLocale() {
        MutableLiveData<String> data = new MutableLiveData<>();
        data.setValue(getUserSession().getLocale());
        return data;
    }

    public void setLocale(String locale) {
        getUserSession().setLocale(locale);
    }

    public LiveData<Boolean> wasLanguageChanged() {
        MutableLiveData<Boolean> data = new MutableLiveData<>();
        data.setValue(getUserSession().wasLanguageChanged());
        return data;
    }

    public void wasLanguageChanged(boolean wasLanguageChanged) {
        getUserSession().wasLanguageChanged(wasLanguageChanged);
    }
}
