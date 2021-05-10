package com.android.tutorial.check_weather.rest

import com.android.tutorial.check_weather.model.Icon
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {
    companion object {
        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .client(getClient())
                .addConverterFactory(getConverter())
                .build()

            return retrofit.create(ApiService::class.java)
        }

        private fun getClient(): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val apiKeyInterceptor = AppIdInterceptor("f3fe814b0ab72bf149c254ea0b065970")

            return OkHttpClient.Builder()
                .addInterceptor(apiKeyInterceptor)
                .addInterceptor(loggingInterceptor)
                .addInterceptor { chain ->
                    //sample inline interceptor
                    chain.proceed(chain.request())
                }
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        private fun getConverter(): GsonConverterFactory {
            val data = GsonBuilder()
                .registerTypeAdapter(Icon::class.java, IconDeserializer())
                .create()

            return GsonConverterFactory.create(data)
        }
    }
}