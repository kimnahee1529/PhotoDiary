package com.todaylab.cleanarchtemplate.ui.model

import java.util.Date

data class WeatherState(
    val date: Date,
    val lat: Double,
    val lon: Double,
    val main: String,
    val description: String,
    val icon: String,
)

