package com.wojdor.hearthstonecards.data.service

import com.wojdor.hearthstonecards.data.service.model.CardModel
import com.wojdor.hearthstonecards.data.service.model.VersionInfoModel

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CardApi {

    @GET("info")
    fun getVersionInfo(): Single<VersionInfoModel>

    @GET("cards/classes/{class}?collectible=1")
    fun getCollectibleCardsForClass(@Path("class") className: String,
                                    @Query("locale") locale: String): Single<List<CardModel>>
}
