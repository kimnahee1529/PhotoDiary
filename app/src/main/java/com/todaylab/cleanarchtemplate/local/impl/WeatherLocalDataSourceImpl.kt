package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun getWeather(lat: Double, long: Double): WeatherEntity? {
        // get saved weather entity
        val weather = weatherDao.getWeather() ?: return null

        // check if lat, long, date match
        // todo: check if date matches
        if (weather.lat != lat || weather.long != long) {
            weatherDao.deleteWeather()
            return null
        }

        return weather.toData()
    }

    override suspend fun saveCurrentWeather(weather: WeatherEntity): Boolean {
        try {
            weatherDao.saveWeather(weather.toLocal())
            return true
        } catch (e: Exception) {
            return false
        }
    }
}