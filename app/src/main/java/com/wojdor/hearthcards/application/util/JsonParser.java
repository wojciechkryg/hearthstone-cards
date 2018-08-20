package com.wojdor.hearthcards.application.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import timber.log.Timber;

public class JsonParser {

    private Gson gson = new Gson();

    public <T> String toJson(T object) {
        return gson.toJson(object);
    }

    public <T> T fromJson(Class<T> type, String json) {
        T object = null;
        try {
            object = gson.fromJson(json, type);
        } catch (JsonSyntaxException error) {
            Timber.e(error);
        }
        return object;
    }
}
