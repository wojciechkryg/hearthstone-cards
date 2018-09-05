package com.wojdor.hearthstonecards.data.service;

public final class CardService {

    private static final String BASE_URL = "https://omgvamp-hearthstone-v1.p.mashape.com/";
    private static final Object LOCK = new Object();

    private static CardApi instance;

    private CardService() {
    }

    public static CardApi getInstance() {
        if (instance == null) {
            synchronized (LOCK) {
                instance = ServiceGenerator.getInstance(BASE_URL).createService(CardApi.class);
            }
        }
        return instance;
    }
}
