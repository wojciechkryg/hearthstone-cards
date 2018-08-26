package com.wojdor.hearthcards.application.splash;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthcards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthcards.data.service.mapper.VersionInfoMapper;
import com.wojdor.hearthcards.domain.VersionInfo;

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
        disposable.add(cardApi.getVersionInfo(userSession.getLocale())
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
}
