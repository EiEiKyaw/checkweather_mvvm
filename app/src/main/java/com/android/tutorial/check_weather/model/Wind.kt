package com.android.tutorial.check_weather.model

import com.google.gson.annotations.SerializedName

class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val degree: Double,
) {
}