package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.domain.model.Weather

interface WeatherLocalDataSource {

    suspend fun saveWeather(weather: Weather)
}