package com.todaylab.photodiary.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todaylab.photodiary.domain.model.WeatherType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "diary")
data class DiaryLocal(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,

    val title: String,            // 제목
    val date: LocalDate,          // 날짜
    val weather: WeatherType,          // 날씨 (☀️🌧️ 등 enum/string)
    val wakeTime: LocalTime,      // 일어난 시간
    val sleepTime: LocalTime,     // 잠든 시간
    val content: String,          // 내용
    val photoUris: List<String>,  // 선택한 사진들 (Uri를 String으로 저장)
)