package com.wojdor.hearthstonecards.data.service

import com.wojdor.hearthstonecards.data.service.interceptor.TokenInterceptor

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ServiceGenerator private constructor(baseUrl: String) {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(baseUrl)
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

    companion object {
        private val LOCK = Any()
        private var instance: ServiceGenerator? = null

        fun getInstance(baseUrl: String): ServiceGenerator {
            if (instance == null) {
                synchronized(LOCK) {
                    instance = ServiceGenerator(baseUrl)
                }
            }
            return instance as ServiceGenerator
        }
    }
}
