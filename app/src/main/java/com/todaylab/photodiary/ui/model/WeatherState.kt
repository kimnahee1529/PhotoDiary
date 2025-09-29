package com.todaylab.photodiary.ui.model

import com.todaylab.photodiary.domain.model.WeatherType
import java.util.Date

data class WeatherState(
    val state: WeatherType = WeatherType.ETC
) {
    companion object {
        fun getIconLabel(icon: String): WeatherType {
            return when (icon) {
                //day
                "01d", "01n", "02d", "02n" -> WeatherType.SUNNY
                "03d", "03n", "04d", "04n", "50d", "50n" -> WeatherType.CLOUDY
                "09d", "09n", "10d", "10n", "11d", "11n" -> WeatherType.RAINY
                "13d", "13n" -> WeatherType.SNOWY
                else -> WeatherType.ETC
            }
        }
    }
}

