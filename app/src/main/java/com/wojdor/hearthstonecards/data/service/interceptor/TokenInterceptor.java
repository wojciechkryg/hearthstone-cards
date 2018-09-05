package com.wojdor.hearthstonecards.data.service.interceptor;

import android.support.annotation.NonNull;

import com.wojdor.hearthstonecards.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {

    private static final String API_KEY = "X-Mashape-Key";

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader(API_KEY, BuildConfig.HEARTHSTONE_API_KEY)
                .build();
        return chain.proceed(request);
    }
}
