package com.android.tutorial.check_weather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.tutorial.check_weather.model.WeatherResponse
import com.android.tutorial.check_weather.rest.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val errorText = MutableLiveData<String>()
    val weatherData = MutableLiveData<WeatherResponse>()

    fun loadData(city: String) {
        isLoading.value = true

        RestClient.getApiService()
            .getCurrentWeather(city)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        Log.d("dataChanged", "..............................................................success")
                        response.body()?.let { weatherResponse ->
                            Log.d("load", "loading data .... $city")
                            weatherData.value = weatherResponse
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    isLoading.value = false
                    errorText.value = t.message ?: "Unknown Error"
                }
            })
    }

    fun loadDataByLatLon(lat: String, lon: String) {
        isLoading.value = true

        RestClient.getApiService()
            .getCurrentWeatherByLatLon(lat, lon)
            .enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        response.body()?.let { weatherResponse ->
                            weatherData.value = weatherResponse
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    isLoading.value = false
                    errorText.value = t.message ?: "Unknown Error"
                }

            })
    }

}