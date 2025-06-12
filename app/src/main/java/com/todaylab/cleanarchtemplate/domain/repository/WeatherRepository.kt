package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.domain.model.Weather


interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Weather
    suspend fun saveWeather(weather: Weather)

}