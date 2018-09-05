package com.wojdor.hearthstonecards.application.card;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthstonecards.application.util.FileStorage;
import com.wojdor.hearthstonecards.domain.Card;

import java.io.File;

public class CardViewModel extends BaseAndroidViewModel {

    private FileStorage fileStorage;

    public CardViewModel(@NonNull Application application) {
        super(application);
        this.fileStorage = new FileStorage(application);
    }

    public LiveData<Card> getCardByCardId(String cardId) {
        return getCardDao().getCardByCardId(cardId);
    }

    public LiveData<File> getCardImage(String cardId) {
        MutableLiveData<File> data = new MutableLiveData<>();
        File file = fileStorage.get(cardId);
        data.postValue(file);
        return data;
    }
}
