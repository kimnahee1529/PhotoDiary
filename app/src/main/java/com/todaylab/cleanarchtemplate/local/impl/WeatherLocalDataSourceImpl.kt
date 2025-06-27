package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import javax.inject.Inject
import kotlin.math.abs
import kotlin.time.Duration.Companion.hours

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun getFreshWeather(lat: Double, lon: Double): DataResource<WeatherEntity> {
        val savedWeather = weatherDao.getWeather() ?: return DataResource.empty()

        val locationTolerance = 0.0001 // tolerance for lat/lon comparison
        val isLocationMatch = abs(savedWeather.lat - lat) < locationTolerance &&
                abs(savedWeather.lon - lon) < locationTolerance

        val threeHoursAgo = System.currentTimeMillis() - 3.hours.inWholeMilliseconds
        val isDateWithin3Hours = savedWeather.date.time >= threeHoursAgo

        if (isLocationMatch && isDateWithin3Hours) {
            return DataResource.success(savedWeather.toData())
        } else {
            weatherDao.deleteWeather()
            return DataResource.empty()
        }
    }

    override suspend fun saveWeather(weather: WeatherEntity) {
        weatherDao.saveWeather(weather.toLocal())
    }
}