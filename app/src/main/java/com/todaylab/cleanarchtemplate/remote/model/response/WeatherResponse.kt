package com.todaylab.cleanarchtemplate.remote.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind? = null,
    val clouds: Clouds? = null,
    val rain: Rain? = null,
    val snow: Snow? = null,
    val dt: Long,
    val sys: Sys? = null,
    val timezone: Int,
    val id: Long,
    val name: String,
    val cod: Int,
)

@Serializable
data class Coord(
    val lon: Double,
    val lat: Double,
)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
)

@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Int? = null,
    @SerialName("grnd_level")
    val grndLevel: Int? = null,
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double? = null,
)

@Serializable
data class Clouds(
    val all: Int,
)

@Serializable
data class Rain(
    @SerialName("1h")
    val oneHour: Double,
)

@Serializable
data class Snow(
    @SerialName("1h")
    val oneHour: Double,
)

@Serializable
data class Sys(
    val type: Int? = null,
    val id: Int? = null,
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)
