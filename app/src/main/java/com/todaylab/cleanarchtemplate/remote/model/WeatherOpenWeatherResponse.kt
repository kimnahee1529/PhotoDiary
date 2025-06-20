package com.todaylab.cleanarchtemplate.remote.model

import kotlinx.serialization.SerialName

/**
 * remote layer
 * open weather api response model
 */
data class WeatherOpenWeatherResponse(
    val daily: List<Daily>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    @SerialName("timezone_offset")
    val timezoneOffset: Int
) {
    data class Daily(
        val clouds: Int,
        @SerialName("dew_point")
        val dewPoint: Double,
        val dt: Int,
        @SerialName("feels_like")
        val feelsLike: FeelsLike,
        val humidity: Int,
        @SerialName("moon_phase")
        val moonPhase: Double,
        val moonrise: Int,
        val moonset: Int,
        val pop: Double,
        val pressure: Int,
        val rain: Double,
        val summary: String,
        val sunrise: Int,
        val sunset: Int,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        @SerialName("wind_deg")
        val windDeg: Int,
        @SerialName("wind_gust")
        val windGust: Double,
        @SerialName("wind_speed")
        val windSpeed: Double
    ) {
        data class FeelsLike(
            val day: Double,
            val eve: Double,
            val morn: Double,
            val night: Double
        )

        data class Temp(
            val day: Double,
            val eve: Double,
            val max: Double,
            val min: Double,
            val morn: Double,
            val night: Double
        )

        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )
    }
}