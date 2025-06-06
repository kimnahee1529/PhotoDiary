package com.todaylab.cleanarchtemplate.remote.model

/**
 * remote layer
 * google weather api response model
 * @deprecated not used
 */
data class WeatherGoogleWeatherResponse(
    val airPressure: AirPressure,
    val cloudCover: Int,
    val currentConditionsHistory: CurrentConditionsHistory,
    val currentTime: String,
    val dewPoint: DewPoint,
    val feelsLikeTemperature: FeelsLikeTemperature,
    val heatIndex: HeatIndex,
    val isDaytime: Boolean,
    val precipitation: Precipitation,
    val relativeHumidity: Int,
    val temperature: Temperature,
    val thunderstormProbability: Int,
    val timeZone: TimeZone,
    val uvIndex: Int,
    val visibility: Visibility,
    val weatherCondition: WeatherCondition,
    val wind: Wind,
    val windChill: WindChill
) {
    data class AirPressure(
        val meanSeaLevelMillibars: Double
    )

    data class CurrentConditionsHistory(
        val maxTemperature: MaxTemperature,
        val minTemperature: MinTemperature,
        val qpf: Qpf,
        val temperatureChange: TemperatureChange
    ) {
        data class MaxTemperature(
            val degrees: Double,
            val unit: String
        )

        data class MinTemperature(
            val degrees: Double,
            val unit: String
        )

        data class Qpf(
            val quantity: Int,
            val unit: String
        )

        data class TemperatureChange(
            val degrees: Double,
            val unit: String
        )
    }

    data class DewPoint(
        val degrees: Double,
        val unit: String
    )

    data class FeelsLikeTemperature(
        val degrees: Double,
        val unit: String
    )

    data class HeatIndex(
        val degrees: Double,
        val unit: String
    )

    data class Precipitation(
        val probability: Probability,
        val qpf: Qpf
    ) {
        data class Probability(
            val percent: Int,
            val type: String
        )

        data class Qpf(
            val quantity: Int,
            val unit: String
        )
    }

    data class Temperature(
        val degrees: Double,
        val unit: String
    )

    data class TimeZone(
        val id: String
    )

    data class Visibility(
        val distance: Int,
        val unit: String
    )

    data class WeatherCondition(
        val description: Description,
        val iconBaseUri: String,
        val type: String
    ) {
        data class Description(
            val languageCode: String,
            val text: String
        )
    }

    data class Wind(
        val direction: Direction,
        val gust: Gust,
        val speed: Speed
    ) {
        data class Direction(
            val cardinal: String,
            val degrees: Int
        )

        data class Gust(
            val unit: String,
            val value: Int
        )

        data class Speed(
            val unit: String,
            val value: Int
        )
    }

    data class WindChill(
        val degrees: Double,
        val unit: String
    )
}