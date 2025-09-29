package com.todaylab.photodiary.data.model

import com.todaylab.photodiary.domain.model.WeatherType
import java.time.LocalDate
import java.time.LocalTime

data class DiaryEntity(
    val id: Long = 0L,
    val title: String,
    val date: LocalDate,
    val weather: WeatherType,       // 날씨는 enum으로 쓰면 안전해요
    val wakeTime: LocalTime,
    val sleepTime: LocalTime,
    val content: String,
    val photoUris: List<String>
)