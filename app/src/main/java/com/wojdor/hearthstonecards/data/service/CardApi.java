package com.wojdor.hearthstonecards.data.service;

import com.wojdor.hearthstonecards.data.service.model.CardModel;
import com.wojdor.hearthstonecards.data.service.model.VersionInfoModel;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CardApi {

    @GET("info")
    Single<VersionInfoModel> getVersionInfo();

    @GET("cards/classes/{class}?collectible=1")
    Single<List<CardModel>> getCollectibleCardsForClass(@Path("class") String className,
                                                        @Query("locale") String locale);
}
