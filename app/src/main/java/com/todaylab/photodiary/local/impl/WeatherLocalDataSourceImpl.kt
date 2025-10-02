package com.todaylab.photodiary.local.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.local.WeatherLocalDataSource
import com.todaylab.photodiary.data.model.WeatherEntity
import com.todaylab.photodiary.local.mapper.WeatherMapper
import com.todaylab.photodiary.local.room.dao.WeatherDao
import timber.log.Timber
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun get(): DataResource<WeatherEntity> {
        try {
            val savedWeather = weatherDao.get() ?: return DataResource.loading()
            return DataResource.success(WeatherMapper.mapToHigh(savedWeather))
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun getByLocation(lat: Double, lon: Double): DataResource<WeatherEntity> {
        try {
            val savedWeather = weatherDao.getByLocation(lat, lon) ?: return DataResource.loading()
            return DataResource.success(WeatherMapper.mapToHigh(savedWeather))
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun save(item: WeatherEntity): Boolean {
        try {
            weatherDao.save(WeatherMapper.mapToLow(item))
            return true
        } catch (e: Exception) {
            Timber.e("local layer error - ${e.message}")
            return false
        }
    }

    override suspend fun delete(): Boolean {
        try {
            weatherDao.delete()
            return true
        } catch (e: Exception) {
            Timber.e("local layer error - ${e.message}")
            return false
        }
    }
}