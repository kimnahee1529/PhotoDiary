package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

interface WeatherLocalDataSource {
    suspend fun getWeather(lat: Double, lon: Double): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
}