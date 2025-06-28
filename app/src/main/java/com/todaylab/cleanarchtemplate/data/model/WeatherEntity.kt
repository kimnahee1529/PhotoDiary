package com.todaylab.cleanarchtemplate.data.model

import java.util.Date

// todo: remove unused properties
data class WeatherEntity (
    val date: Date,
    val lat: Double,
    val lon: Double,
    val cityName: String,
    val temp: Double,
    val weatherMain: String,
    val weatherDesc: String,
    val weatherIcon: String,
    val timestamp: Long,
)