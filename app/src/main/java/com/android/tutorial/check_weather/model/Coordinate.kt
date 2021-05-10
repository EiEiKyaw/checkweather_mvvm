package com.android.tutorial.check_weather.model

import com.google.gson.annotations.SerializedName

class Coordinate(
    @SerializedName("lon") val lon: Double,
    @SerializedName("lat") val lat: Double
) {
}