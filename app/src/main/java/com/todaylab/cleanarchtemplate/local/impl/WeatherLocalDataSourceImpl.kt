package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import javax.inject.Inject

/**
 * local layer
 * weather local data source implementation
 */
class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun getCurrentWeather(lat: Double, long: Double): WeatherEntity? {
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