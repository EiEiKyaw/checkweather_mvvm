package com.android.tutorial.check_weather.model

import com.google.gson.annotations.SerializedName

class SunCondition(
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Long,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
) {
}