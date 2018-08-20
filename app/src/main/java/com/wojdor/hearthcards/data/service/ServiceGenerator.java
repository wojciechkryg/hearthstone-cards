package com.wojdor.hearthcards.data.service;

import com.wojdor.hearthcards.data.service.interceptor.TokenInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ServiceGenerator {

    private static final Object LOCK = new Object();

    private static ServiceGenerator instance;

    private Retrofit retrofit;

    private ServiceGenerator(String baseUrl) {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build();
    }

    public static ServiceGenerator getInstance(String baseUrl) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = new ServiceGenerator(baseUrl);
            }
        }
        return instance;
    }

    private OkHttpClient createOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new TokenInterceptor())
                .build();
    }

    public <T> T createService(Class<T> service) {
        return retrofit.create(service);
    }
}
