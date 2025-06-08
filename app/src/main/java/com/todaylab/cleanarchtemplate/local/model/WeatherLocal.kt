package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

/**
 * local layer
 * weather local model
 * room db entity
 */
@Entity
data class WeatherLocal(
    @PrimaryKey val id: Int = 0,
    val long: Double,
    val lat: Double,
    val date: Date = Date(),
    val main: String,
    val description: String,
    val maxTemp: Double,
    val minTemp: Double
)