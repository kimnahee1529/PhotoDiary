package com.todaylab.cleanarchtemplate.remote.model.response

import kotlinx.serialization.SerialName

data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int
)

data class Coord(
    val lon: Double,
    val lat: Double
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Double,
    @SerialName("feelsLike")
    val feels_like: Double,
    @SerialName("tempMin")
    val temp_min: Double,
    @SerialName("tempMax")
    val temp_max: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("seaLevel")
    val sea_level: Int?,
    @SerialName("grndLevel")
    val grnd_level: Int?
)

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)

data class Clouds(
    val all: Int
)

data class Rain(
    @SerialName("1h")
    val oneHour: Double
)

data class Snow(
    @SerialName("1h")
    val oneHour: Double
)

data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    val sunrise: Long,
    val sunset: Long
)
