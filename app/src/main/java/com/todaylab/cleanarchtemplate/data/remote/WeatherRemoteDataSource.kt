package com.todaylab.cleanarchtemplate.data.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.remote.model.response.Weather

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double): WeatherEntity

}