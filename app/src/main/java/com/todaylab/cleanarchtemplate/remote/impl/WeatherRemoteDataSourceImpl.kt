package com.todaylab.cleanarchtemplate.remote.impl

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.remote.api.GoogleWeatherApiService
import com.todaylab.cleanarchtemplate.remote.api.OpenWeatherApiService
import com.todaylab.cleanarchtemplate.remote.toData
import javax.inject.Inject

/**
 * remote layer
 * weather remote data source implementation
 */
class WeatherRemoteDataSourceImpl @Inject constructor(
    private val googleWeatherApiService: GoogleWeatherApiService,
    private val openWeatherApiService: OpenWeatherApiService
) : WeatherRemoteDataSource {
    override suspend fun getCurrentWeather(lat: Double, long: Double): WeatherEntity {
        // google weather api call
        // val response = googleWeatherApiService.getCurrentWeather(lat = lat, long = long)

        // open weather api call
        val response = openWeatherApiService.getCurrentWeather(lat = lat, long = long)
        return response.body()?.toData(lat, long) ?: throw Exception(response.message())
    }
}