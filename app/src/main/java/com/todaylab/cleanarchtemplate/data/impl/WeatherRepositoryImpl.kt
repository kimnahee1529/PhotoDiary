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
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): Weather {

        // get weather from local data source if exists
        val localWeather = weatherLocalDataSource.getWeather(lat, lon)
        if (localWeather != null) return localWeather.toDomain()

        // get weather from remote data source if not exists
        // and save it to local data source
        val remoteWeather = weatherRemoteDataSource.getWeather(lat, lon)
        weatherLocalDataSource.saveWeather(remoteWeather)
        return remoteWeather.toDomain()
    }
}