package com.wojdor.hearthcards.application.update;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.os.ResultReceiver;

import com.wojdor.hearthcards.application.util.CardImageDownloader;
import com.wojdor.hearthcards.data.database.CardDatabase;
import com.wojdor.hearthcards.data.database.dao.CardDao;
import com.wojdor.hearthcards.data.service.CardApi;
import com.wojdor.hearthcards.data.service.CardService;
import com.wojdor.hearthcards.data.service.mapper.CardMapper;
import com.wojdor.hearthcards.data.service.mapper.ZipResultMapper;
import com.wojdor.hearthcards.data.service.model.CardModel;
import com.wojdor.hearthcards.data.session.UserSession;
import com.wojdor.hearthcards.domain.Card;
import com.wojdor.hearthcards.domain.VersionInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class UpdateIntentService extends IntentService {

    private static final String VERSION_INFO_EXTRA = "VERSION_INFO_EXTRA";
    private static final String UPDATE_RESULT_RECEIVER_EXTRA = "UPDATE_RESULT_RECEIVER_EXTRA";

    private CardApi cardApi;
    private CardDao cardDao;
    private UserSession userSession;
    private CardImageDownloader cardImageDownloader;

    public UpdateIntentService() {
        super(UpdateIntentService.class.getSimpleName());
        cardApi = CardService.getInstance();
        cardDao = CardDatabase.getInstance(this).cardDao();
        userSession = UserSession.getInstance(this);
        cardImageDownloader = new CardImageDownloader(this);
    }

    public static void update(Context context, VersionInfo versionInfo, UpdateResultReceiver updateResultReceiver) {
        Intent intent = new Intent(context, UpdateIntentService.class);
        intent.putExtra(VERSION_INFO_EXTRA, versionInfo);
        intent.putExtra(UPDATE_RESULT_RECEIVER_EXTRA, updateResultReceiver);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        VersionInfo versionInfo = intent.getExtras().getParcelable(VERSION_INFO_EXTRA);
        ResultReceiver updateResultReceiver = intent.getExtras().getParcelable(UPDATE_RESULT_RECEIVER_EXTRA);
        String locale = userSession.getLocale();
        List<Single<List<CardModel>>> apiCalls = new ArrayList<>();
        for (String className : versionInfo.getClassNames()) {
            apiCalls.add(downloadDataForClass(className, locale));
        }
        makeAllApiCalls(apiCalls, versionInfo, updateResultReceiver);
    }

    private void makeAllApiCalls(List<Single<List<CardModel>>> apiCalls, VersionInfo versionInfo, ResultReceiver updateResultReceiver) {
        Single.zip(apiCalls, ZipResultMapper::map)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(cardModels -> onSuccess(cardModels, versionInfo, updateResultReceiver),
                        error -> onError(updateResultReceiver));
    }

    private Single downloadDataForClass(String className, String locale) {
        return cardApi.getCollectibleCardsForClass(className, locale)
                .onErrorReturnItem(Collections.emptyList());
    }

    @SuppressLint("RestrictedApi")
    private void onSuccess(List<CardModel> cardModels, VersionInfo versionInfo, ResultReceiver updateResultReceiver) {
        String locale = userSession.getLocale();
        for (CardModel cardModel : cardModels) {
            cardImageDownloader.getImage(cardModel.getCardId(), locale);
        }
        List<Card> cards = CardMapper.map(cardModels);
        cardDao.insertCards(cards);
        userSession.setVersionInfo(versionInfo);
        updateResultReceiver.send(UpdateResultReceiver.RESULT_SUCCESS, null);
    }

    @SuppressLint("RestrictedApi")
    private void onError(ResultReceiver updateResultReceiver) {
        updateResultReceiver.send(UpdateResultReceiver.RESULT_ERROR, null);
    }
}
