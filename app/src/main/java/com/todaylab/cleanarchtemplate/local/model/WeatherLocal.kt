package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_weather")
data class WeatherLocal(
    @PrimaryKey
    val userId: String,
    val main: String,
    val description: String,
    val icon: String
)