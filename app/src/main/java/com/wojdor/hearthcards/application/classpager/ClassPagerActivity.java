package com.wojdor.hearthcards.application.classpager;

import android.os.Bundle;

import com.wojdor.hearthcards.R;
import com.wojdor.hearthcards.application.base.BaseActivity;
import com.wojdor.hearthcards.application.util.FileWriter;
import com.wojdor.hearthcards.application.util.ImageDownloader;
import com.wojdor.hearthcards.data.service.CardsService;
import com.wojdor.hearthcards.data.service.mapper.CardMapper;
import com.wojdor.hearthcards.data.service.mapper.InfoMapper;
import com.wojdor.hearthcards.data.service.model.CardModel;
import com.wojdor.hearthcards.data.service.model.InfoModel;
import com.wojdor.hearthcards.domain.Card;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ClassPagerActivity extends BaseActivity {

    private FileWriter fileWriter = new FileWriter(this);
    private ImageDownloader imageDownloader = new ImageDownloader(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_pager);

        CardsService.getInstance().getCollectibleCardsForClass("Death Knight")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Dream")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Druid")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Priest")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Warrior")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Paladin")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Warlock")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Shaman")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Mage")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Rogue")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Hunter")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);
        CardsService.getInstance().getCollectibleCardsForClass("Neutral")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess, this::onError);

        CardsService.getInstance().getInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onSuccess2, this::onError);
    }

    private void onSuccess(List<CardModel> cardModels) {
        for (CardModel cardModel : cardModels) {
            imageDownloader.getImage(cardModel.getCardId(), cardModel.getImg());
        }
        List<Card> cards = CardMapper.map(cardModels);
        //TODO: Save cards to database
    }

    private void onSuccess2(InfoModel infoModel) {
        InfoMapper.map(infoModel);
    }

    private void onError(Throwable error) {
        Timber.e(error);
    }
}
