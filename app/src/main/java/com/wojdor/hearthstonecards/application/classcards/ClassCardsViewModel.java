package com.wojdor.hearthstonecards.application.classcards;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthstonecards.domain.Card;

import java.util.List;

public class ClassCardsViewModel extends BaseAndroidViewModel {

    public ClassCardsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Card>> getCardsFromClass(String className) {
        return cardDao.getCardsFromClass(className);
    }
}
