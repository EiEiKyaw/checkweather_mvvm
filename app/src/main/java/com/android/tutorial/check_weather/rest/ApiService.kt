package com.android.tutorial.check_weather.rest

import com.android.tutorial.check_weather.model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") q: String,
    ): Call<WeatherResponse>

    @GET("weather")
    fun getCurrentWeatherByLatLon(
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): Call<WeatherResponse>

}