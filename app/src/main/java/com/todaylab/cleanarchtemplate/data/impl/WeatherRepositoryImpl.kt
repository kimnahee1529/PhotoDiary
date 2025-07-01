package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.mapper.WeatherMapper
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject
import kotlin.time.Duration.Companion.hours

/**
 * local weather data should be expired after 3 hours
 */
const val WEATHER_EXPIRATION_HOUR = 3

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : WeatherRepository {
    /**
     * Get singleton weather entity from local
     */
    override suspend fun get(): DataResource<Weather> {
        try {
            val localWeather = weatherLocalDataSource.get()
            Timber.d("weather repo impl - local weather: ${localWeather}")
            return localWeather.mapData(WeatherMapper::mapToHigh)
        } catch (e: Exception) {
            return DataResource.error(Throwable("data layer error - ${e.message}"))
        }
    }

    /**
     * Get singleton weather entity that matches location
     * If not exists or expired, fetch from remote and save to local
     */
    override suspend fun getByLocation(lat: Double, lon: Double): DataResource<Weather> {
        val localWeather = weatherLocalDataSource.getByLocation(lat, lon)
        Timber.d("weather repo impl - local weather: ${localWeather}")
        if (localWeather is DataResource.Success) {
            val expirationTime =
                System.currentTimeMillis() - WEATHER_EXPIRATION_HOUR.hours.inWholeMilliseconds

            return if (localWeather.data.date.time >= expirationTime) {
                Timber.d("local weather has not expired")
                DataResource.success(WeatherMapper.mapToHigh(localWeather.data))
            } else {
                Timber.d("local weather has expired")
                DataResource.empty()
            }
        }

        val remoteWeather = weatherRemoteDataSource.getWeather(lat, lon)
        Timber.d("weather repo impl - remote weather: ${remoteWeather}")
        return remoteWeather.mapData(WeatherMapper::mapToHigh)
    }

    override suspend fun save(item: Weather): Boolean {
        return try {
            weatherLocalDataSource.save(WeatherMapper.mapToLow(item))
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            false
        }
    }

    override suspend fun delete(): Boolean {
        return try {
            weatherLocalDataSource.delete()
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            false
        }
    }
}
