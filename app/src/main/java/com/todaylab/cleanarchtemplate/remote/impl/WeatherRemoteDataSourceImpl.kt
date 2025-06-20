package com.todaylab.cleanarchtemplate.remote.impl

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.remote.api.WeatherApiService
import com.todaylab.cleanarchtemplate.remote.toData
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double): WeatherEntity {
        val response = api.getWeather(lat, lon)
        if (response.isSuccessful) {
            val body = response.body() ?: throw Exception("Empty body")
            return body.toData()
        } else {
            throw Exception("API error: ${response.code()}")
        }
    }

}
