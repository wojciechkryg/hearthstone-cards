package com.wojdor.hearthcards.application.classpager;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.wojdor.hearthcards.application.base.BaseAndroidViewModel;
import com.wojdor.hearthcards.application.util.ImageDownloader;
import com.wojdor.hearthcards.data.service.mapper.CardMapper;
import com.wojdor.hearthcards.data.service.mapper.VersionInfoMapper;
import com.wojdor.hearthcards.data.service.model.CardModel;
import com.wojdor.hearthcards.domain.Card;
import com.wojdor.hearthcards.domain.VersionInfo;

import java.util.List;

import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ClassPagerViewModel extends BaseAndroidViewModel {

    private ImageDownloader imageDownloader;

    public ClassPagerViewModel(@NonNull Application application) {
        super(application);
        imageDownloader = new ImageDownloader(application);
    }

    public LiveData<VersionInfo> getRemoteVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        disposable.add(cardApi.getVersionInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(versionInfoModel -> {
                            VersionInfo versionInfo = VersionInfoMapper.map(versionInfoModel);
                            userSession.setVersionInfo(versionInfo);
                            data.postValue(versionInfo);
                        },
                        error -> data.postValue(null)));
        return data;
    }

    public LiveData<VersionInfo> getLocalVersionInfo() {
        MutableLiveData<VersionInfo> data = new MutableLiveData<>();
        data.setValue(userSession.getVersionInfo());
        return data;
    }

    public void downloadNewData(VersionInfo remoteVersionInfo) {
        for (String className : remoteVersionInfo.getClassNames()) {
            downloadDataForClass(className);
        }
    }

    private void downloadDataForClass(String className) {
        cardApi.getCollectibleCardsForClass(className)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(this::onSuccess, Timber::e);
    }

    private void onSuccess(List<CardModel> cardModels) {
        for (CardModel cardModel : cardModels) {
            imageDownloader.getImage(cardModel.getCardId(), cardModel.getImg());
        }
        List<Card> cards = CardMapper.map(cardModels);
        cardDao.insertCards(cards);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
