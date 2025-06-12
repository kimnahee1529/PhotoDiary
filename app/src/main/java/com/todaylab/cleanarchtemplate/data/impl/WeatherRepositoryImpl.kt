package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
): WeatherRepository{
    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return weatherRemoteDataSource.getWeather(lat, lon).toDomain()
    }

    override suspend fun saveWeather(weather: Weather) {
        weatherLocalDataSource.saveWeather(weather)
    }

}

