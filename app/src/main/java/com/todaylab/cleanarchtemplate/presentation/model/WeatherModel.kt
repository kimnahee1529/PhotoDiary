package com.todaylab.cleanarchtemplate.presentation.model

import java.util.Date

data class WeatherModel(
    val date: Date,
    val lat: Double,
    val lon: Double,
    val main: String,
    val description: String,
    val icon: String,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)