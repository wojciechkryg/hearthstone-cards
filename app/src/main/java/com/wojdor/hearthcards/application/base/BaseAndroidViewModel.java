package com.wojdor.hearthcards.application.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.wojdor.hearthcards.data.session.UserSession;
import com.wojdor.hearthcards.data.database.CardDatabase;
import com.wojdor.hearthcards.data.database.dao.CardDao;
import com.wojdor.hearthcards.data.service.CardApi;
import com.wojdor.hearthcards.data.service.CardService;

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
}
