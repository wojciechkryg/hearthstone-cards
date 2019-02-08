package com.wojdor.hearthstonecards.data.service

import com.wojdor.hearthstonecards.data.service.interceptor.TokenInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator(private val url: String) {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createOkHttpClient())
                .build()
    }

    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor())
                .build()
    }

    fun <T> createService(service: Class<T>): T {
        return retrofit.create(service)
    }
}
