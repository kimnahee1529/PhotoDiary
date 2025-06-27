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
        Timber.d("getWeather called")
        // 1. get weather from local
        // todo: 저장된 날씨 정보가 3시간 이내의 정보인 경우, 데이터 갱신하기

        val now = System.currentTimeMillis()
        val THREE_HOURS_IN_MILLIS = 3 * 60 * 60 * 1000L

        val localWeather = weatherLocalDataSource.getWeather(lat, lon)
        if (localWeather != null) {
            val age = now - localWeather.timestamp
            if (age <= THREE_HOURS_IN_MILLIS) {
                Timber.d("3시간 이내 - (1)using recent local weather: ${localWeather.toDomain()}")
                Timber.d("3시간 이내 - (2)using recent local weather: ${localWeather}")
                return DataResource.success(localWeather.toDomain())
            } else {
                Timber.d("3시간 이후 - local weather too old, fetching new data")
            }
        } else {
            Timber.d("3시간 X- no local weather, fetching new data")
        }

        // remote fetch
        val remoteWeather = weatherRemoteDataSource.getWeather(lat, lon)
        when (remoteWeather) {
            is DataResource.Error -> return remoteWeather

            is DataResource.Loading -> {
                return DataResource.loading(remoteWeather.data?.toDomain())
            }

            is DataResource.Success -> {
                weatherLocalDataSource.saveWeather(remoteWeather.data)
                return DataResource.success(remoteWeather.data.toDomain())
            }
        }
    }
}