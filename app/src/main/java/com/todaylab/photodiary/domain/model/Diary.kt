package com.todaylab.photodiary.domain.model

import java.time.LocalDate
import java.time.LocalTime

data class Diary(
    val id: Long = 0L,
    val title: String,
    val date: LocalDate,
    val weather: WeatherType,
    val wakeTime: LocalTime,
    val sleepTime: LocalTime,
    val content: String,
    val photoUris: List<String>
)

enum class WeatherType {
    SUNNY, CLOUDY, RAINY, SNOWY, ETC
}