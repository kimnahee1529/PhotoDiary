package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

// id of singleton weather local model
const val WEATHER_ID = 0

/**
 * local layer
 * weather local model
 * room db entity
 */
@Entity(tableName = "user_weather")
data class WeatherLocal(
    @PrimaryKey val id: Int = WEATHER_ID,
    val long: Double,
    val lat: Double,
    val date: Date,
    val main: String,
    val description: String,
    val icon: String
)