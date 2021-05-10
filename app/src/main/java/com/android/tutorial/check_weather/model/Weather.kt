package com.android.tutorial.check_weather.model

import com.google.gson.annotations.SerializedName

class Weather(
    @SerializedName("id") val id: Long,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: Icon,
) {
}