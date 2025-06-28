package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import timber.log.Timber
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
) : WeatherLocalDataSource {
    override suspend fun get(): DataResource<WeatherEntity> {
        try {
            val savedWeather = weatherDao.get() ?: return DataResource.empty()
            return DataResource.success(savedWeather.toData())
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun getByLocation(lat: Double, lon: Double): DataResource<WeatherEntity> {
        try {
            val savedWeather = weatherDao.getByLocation(lat, lon) ?: return DataResource.empty()
            return DataResource.success(savedWeather.toData())
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun save(item: WeatherEntity): Boolean {
        try {
            weatherDao.save(item.toLocal())
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