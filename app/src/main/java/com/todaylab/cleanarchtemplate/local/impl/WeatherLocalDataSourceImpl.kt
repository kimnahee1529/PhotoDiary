package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun getWeather(lat: Double, lon: Double): WeatherEntity? {
        // get saved weather entity
        val weather = weatherDao.getWeather() ?: return null
        // todo: check if date, lat, lon matches
//        if (weather.lat != lat || weather.long != long) {
//            weatherDao.deleteWeather()
//            return null
//        }

        return weather.toData()
    }

    override suspend fun saveWeather(weather: WeatherEntity) {
        weatherDao.saveWeather(weather.toLocal())
    }
}