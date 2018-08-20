package com.wojdor.hearthcards.data.service;

import com.wojdor.hearthcards.data.service.model.CardModel;
import com.wojdor.hearthcards.data.service.model.VersionInfoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CardApi {

    @GET("info")
    Single<VersionInfoModel> getVersionInfo();

    @GET("cards/classes/{class}?collectible=1")
    Single<List<CardModel>> getCollectibleCardsForClass(@Path("class") String className);
}
