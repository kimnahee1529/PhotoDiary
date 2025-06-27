package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.data.toDomain
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
    override suspend fun getWeather(lat: Double, lon: Double): DataResource<Weather> {
        val localWeather = weatherLocalDataSource.getWeather(lat, lon)
        Timber.d("weather repo impl - local weather: ${localWeather}")
        if (localWeather is DataResource.Success) {
            val expirationTime =
                System.currentTimeMillis() - WEATHER_EXPIRATION_HOUR.hours.inWholeMilliseconds

            return if (localWeather.data.date.time >= expirationTime) {
                Timber.d("local weather has not expired")
                DataResource.success(localWeather.data.toDomain())
            } else {
                Timber.d("local weather has expired")
                DataResource.empty()
            }
        }

        val remoteWeather = weatherRemoteDataSource.getWeather(lat, lon)
        Timber.d("weather repo impl - remote weather: ${remoteWeather}")

        // todo: refactor code using mapData function
        return when (remoteWeather) {
            is DataResource.Success -> DataResource.success(remoteWeather.data.toDomain())
            is DataResource.Empty -> DataResource.empty()
            is DataResource.Loading -> DataResource.loading(remoteWeather.data?.toDomain())
            is DataResource.Error -> DataResource.error(remoteWeather.throwable)
        }
    }
}
