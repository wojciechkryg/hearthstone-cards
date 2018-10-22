package com.wojdor.hearthstonecards.data.service

class CardService {

    val cardApi by lazy {
        ServiceGenerator(BASE_URL).createService(CardApi::class.java)
    }

    companion object {
        private const val BASE_URL = "https://omgvamp-hearthstone-v1.p.mashape.com/"
    }
}
