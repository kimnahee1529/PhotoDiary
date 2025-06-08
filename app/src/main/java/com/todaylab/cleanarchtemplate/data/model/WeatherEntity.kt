package com.todaylab.cleanarchtemplate.data.model

import java.util.Date

/**
 * data layer
 * weather model
 */
data class WeatherEntity(
    val long: Double,
    val lat: Double,
    val date: Date = Date(),
    val main: String,
    val description: String,
    val maxTemp: Double,
    val minTemp: Double,
)