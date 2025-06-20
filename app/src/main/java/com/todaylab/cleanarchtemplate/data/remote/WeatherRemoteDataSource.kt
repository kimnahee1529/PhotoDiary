package com.todaylab.cleanarchtemplate.data.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double): WeatherEntity
}