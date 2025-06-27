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
        // todo: fix code
        if (remoteWeather is DataResource.Success || remoteWeather is DataResource.Loading) {
            return DataResource.success(
                remoteWeather.getDataOrNull()?.toDomain()
            ) as DataResource<Weather>
        } else return DataResource.error(Throwable("remote layer error - Unknown error"))
    }
}
