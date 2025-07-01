package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun getWeather(lat: Double, lon: Double): DataResource<WeatherEntity> {
        val savedWeather = weatherDao.getWeather(lat, lon) ?: return DataResource.empty()
        return DataResource.success(savedWeather.toData())
    }

    override suspend fun saveWeather(weather: WeatherEntity) {
        weatherDao.saveWeather(weather.toLocal())
    }
}