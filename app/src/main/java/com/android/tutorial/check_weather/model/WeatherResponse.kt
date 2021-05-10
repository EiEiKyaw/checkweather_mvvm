package com.android.tutorial.check_weather.model

import com.google.gson.annotations.SerializedName

class WeatherResponse(
    @SerializedName("coord") val coord: Coordinate,
    @SerializedName("weather") val weatherList: List<Weather>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: WeatherValues,
    @SerializedName("visibility") val visibility: Double,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("clouds") val clouds: Cloud,
    @SerializedName("dt") val dateTime: Long,
    @SerializedName("sys") val sys: SunCondition,
    @SerializedName("timezone") val timezone: Long,
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("cod") val cod: String,
    ) {
}

//{
//    "coord": {
//    "lon": 96.1561,
//    "lat": 16.8053
//},
//    "weather": [
//    {
//        "id": 802,
//        "main": "Clouds",
//        "description": "scattered clouds",
//        "icon": "03d"
//    }
//    ],
//    "base": "stations",
//    "main": {
//    "temp": 303.97,
//    "feels_like": 305.1,
//    "temp_min": 303.97,
//    "temp_max": 303.97,
//    "pressure": 1010,
//    "humidity": 48,
//    "sea_level": 1010,
//    "grnd_level": 1007
//},
//    "visibility": 7000,
//    "wind": {
//    "speed": 0.68,
//    "deg": 145,
//    "gust": 1.3
//},
//    "clouds": {
//    "all": 40
//},
//    "dt": 1620533445,
//    "sys": {
//    "type": 1,
//    "id": 9322,
//    "country": "MM",
//    "sunrise": 1620515192,
//    "sunset": 1620561428
//},
//    "timezone": 23400,
//    "id": 1298824,
//    "name": "Yangon",
//    "cod": 200
//}