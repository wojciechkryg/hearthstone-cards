package com.wojdor.hearthstonecards.data.service.interceptor

import com.wojdor.hearthstonecards.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class TokenInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader(API_KEY, BuildConfig.HEARTHSTONE_API_KEY)
                .build()
        return chain.proceed(request)
    }

    companion object {
        private const val API_KEY = "X-Mashape-Key"
    }
}
