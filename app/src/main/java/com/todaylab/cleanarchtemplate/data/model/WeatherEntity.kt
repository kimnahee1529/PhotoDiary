package com.todaylab.cleanarchtemplate.data.model

import java.util.Date

/**
 * data layer
 * weather model
 */
data class WeatherEntity(
    val lang: Long,
    val lat: Long,
    val date: Date,
    val summary: String,
    val main: String,
    val description: String,
    val maxTemp: Long,
    val minTemp: Long,
)