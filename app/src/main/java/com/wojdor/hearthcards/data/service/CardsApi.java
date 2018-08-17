package com.wojdor.hearthcards.data.service;

import com.wojdor.hearthcards.data.service.model.CardModel;
import com.wojdor.hearthcards.data.service.model.InfoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CardsApi {

    @GET("info")
    Single<InfoModel> getInfo();

    @GET("cards/classes/{class}?collectible=1")
    Single<List<CardModel>> getCollectibleCardsForClass(@Path("class") String className);
}
