package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * domain layer
 * weather repository interface
 */
interface WeatherRepository {
    suspend fun getCurrentWeather(lat: Double, long: Double): Weather
}