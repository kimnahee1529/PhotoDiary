package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import timber.log.Timber
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) : WeatherRepository {
    override suspend fun getWeather(lat: Double, lon: Double): DataResource<Weather> {
        val localWeather = weatherLocalDataSource.getFreshWeather(lat, lon)
        Timber.d("weather repo impl - local weather: ${localWeather}")
        if (localWeather is DataResource.Success) {
            return DataResource.success(localWeather.data.toDomain())
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
