package com.wojdor.hearthcards.application.card;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthcards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthcards.application.util.FileStorage;
import com.wojdor.hearthcards.domain.Card;

import java.io.File;

public class CardViewModel extends BaseAndroidViewModel {

    private FileStorage fileStorage;

    public CardViewModel(@NonNull Application application) {
        super(application);
        this.fileStorage = new FileStorage(application);
    }

    public LiveData<Card> getCardByCardId(String cardId) {
        return cardDao.getCardByCardId(cardId);
    }

    public LiveData<File> getCardImage(String cardId) {
        MutableLiveData<File> data = new MutableLiveData<>();
        File file = fileStorage.get(cardId);
        data.postValue(file);
        return data;
    }
}
