package com.todaylab.cleanarchtemplate.remote.impl

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.remote.api.GoogleWeatherApiService
import com.todaylab.cleanarchtemplate.remote.toData
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val googleWeatherApiService: GoogleWeatherApiService,
) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeather(lat: Double, long: Double): WeatherEntity {
        val response = googleWeatherApiService.getCurrentWeather(lat = lat, long = long)
        return response.body()?.toData(lat, long) ?: throw Exception(response.message())
    }
}