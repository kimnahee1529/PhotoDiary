package com.todaylab.cleanarchtemplate.ui.model

import java.util.Date

data class WeatherState(
    val date: Date,
    val lat: Double,
    val lon: Double,
    val main: String,
    val description: String,
    val iconLabel: String,
    val icon: String,
) {
    companion object {
        fun getIconLabel(icon: String): String {
            return when (icon) {
                //day
                "01d" -> "맑음" //clear sky
                "02d" -> "조금 흐림" //few clouds
                "03d" -> "흐림" // scattered clouds
                "04d" -> "흐림" //broken clouds
                "09d" -> "비" // shower rain
                "10d" -> "비" //rain
                "11d" -> "천둥번개" //thunderstorm
                "13d" -> "눈" //snow
                "50d" -> "안개" //rain
                //night
                "01n" -> "맑음" //clear sky
                "02n" -> "조금 흐림" //few clouds
                "03n" -> "흐림" // scattered clouds
                "04n" -> "흐림" //broken clouds
                "09n" -> "비" // shower rain
                "10n" -> "비" //rain
                "11n" -> "천둥번개" //thunderstorm
                "13n" -> "눈" //snow
                "50n" -> "안개" //rain
                else -> ""
            }
        }
    }
}

