package com.android.tutorial.check_weather.rest

import okhttp3.Interceptor
import okhttp3.Response

class AppIdInterceptor(
    private val appId:String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.newBuilder()
            .addQueryParameter("appid", appId)
            .build()

        val newRequest = original.newBuilder()
            .url(url)
            .build()

        return chain.proceed(newRequest)
    }
}