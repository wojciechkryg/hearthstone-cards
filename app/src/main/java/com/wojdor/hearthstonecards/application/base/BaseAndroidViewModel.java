package com.wojdor.hearthstonecards.application.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.data.database.CardDatabase;
import com.wojdor.hearthstonecards.data.database.dao.CardDao;
import com.wojdor.hearthstonecards.data.service.CardApi;
import com.wojdor.hearthstonecards.data.service.CardService;
import com.wojdor.hearthstonecards.data.session.UserSession;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseAndroidViewModel extends AndroidViewModel {

    protected CardApi cardApi;
    protected CompositeDisposable disposable;
    protected CardDao cardDao;
    protected UserSession userSession;

    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
        cardApi = CardService.getInstance();
        disposable = new CompositeDisposable();
        cardDao = CardDatabase.getInstance(application).cardDao();
        userSession = UserSession.getInstance(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
