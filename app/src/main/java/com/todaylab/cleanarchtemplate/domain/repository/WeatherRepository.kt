package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * domain layer
 * weather repository interface
 */
interface WeatherRepository {
    fun getCurrentWeather(): Weather
}