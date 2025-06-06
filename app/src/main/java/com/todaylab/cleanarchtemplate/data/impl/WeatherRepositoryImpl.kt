package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
): WeatherRepository{
    override suspend fun getWeather(lat: Double, lon: Double): Weather {
        return weatherRemoteDataSource.getWeather(lat, lon).toDomain()
    }

}

