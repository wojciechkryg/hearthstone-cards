package com.wojdor.hearthcards.application.classpager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthcards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthcards.domain.VersionInfo;

public class ClassPagerViewModel extends BaseAndroidViewModel {

    public ClassPagerViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<VersionInfo> getLocalVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        data.setValue(userSession.getVersionInfo());
        return data;
    }
}
