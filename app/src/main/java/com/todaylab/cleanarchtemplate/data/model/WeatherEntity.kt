package com.todaylab.cleanarchtemplate.data.model

data class WeatherEntity (
    val cityName: String,
    val temp: Double,
    val weatherMain: String,
    val weatherDesc: String,
    val weatherIcon: String,
)