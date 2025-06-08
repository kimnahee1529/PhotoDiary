package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

/**
 * data layer
 * weather local data source interface
 */
interface WeatherLocalDataSource {
    suspend fun getCurrentWeather(lat: Double, long: Double): WeatherEntity?
    suspend fun saveCurrentWeather(weather: WeatherEntity): Boolean
}