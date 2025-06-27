package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

interface WeatherLocalDataSource {
    /**
     * Get weather within 3 hours from local data source
     */
    suspend fun getFreshWeather(lat: Double, lon: Double): DataResource<WeatherEntity>?
    suspend fun saveWeather(weather: WeatherEntity)
}