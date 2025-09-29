package com.todaylab.photodiary.data.remote

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.model.WeatherEntity

interface WeatherRemoteDataSource {
    suspend fun getWeather(lat: Double, lon: Double): DataResource<WeatherEntity>
}