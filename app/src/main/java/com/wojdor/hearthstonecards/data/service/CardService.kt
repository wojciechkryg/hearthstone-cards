package com.wojdor.hearthstonecards.data.service

object CardService {

    private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.mashape.com/"
    private var instance: CardApi? = null

    fun getInstance(): CardApi {
        if (instance == null) {
            instance = ServiceGenerator.getInstance(BASE_URL).createService(CardApi::class.java)
        }
        return instance as CardApi
    }
}
