package com.wojdor.hearthcards.data.service;

public final class CardsService {

    private static final String BASE_URL = "https://omgvamp-hearthstone-v1.p.mashape.com/";

    private static CardsApi instance;

    private CardsService() {
    }

    public static CardsApi getInstance() {
        if (instance == null) {
            instance = ServiceGenerator.getInstance(BASE_URL).createService(CardsApi.class);
        }
        return instance;
    }
}
