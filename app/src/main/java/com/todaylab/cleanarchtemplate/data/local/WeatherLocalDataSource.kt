package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

/**
 * data layer
 * weather local data source interface
 */
interface WeatherLocalDataSource {
    fun getCurrentWeather(): WeatherEntity
    fun saveCurrentWeather(weather: WeatherEntity)
}