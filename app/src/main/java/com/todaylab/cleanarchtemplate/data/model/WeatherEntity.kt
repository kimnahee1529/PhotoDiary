package com.todaylab.cleanarchtemplate.data.model

import java.util.Date

/**
 * data layer
 * weather model
 */
data class WeatherEntity(
    val lang: Double,
    val lat: Double,
    val date: Date = Date(),
    val summary: String? = null,
    val main: String,
    val description: String,
    val maxTemp: Double,
    val minTemp: Double,
)