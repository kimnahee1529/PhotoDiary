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
        // 1. get weather from local
        // todo: 저장된 날씨 정보가 3시간 이내의 정보인 경우, 데이터 갱신하기

        val localWeather = weatherLocalDataSource.getWeather(lat, lon)
        if (localWeather != null) {
            Timber.d("weather repo impl - local weather: ${localWeather.toDomain()}")
            return DataResource.success(localWeather.toDomain())
        }
        Timber.d("weather repo impl - local weather is null")

        // 2. get weather from remote

        val remoteWeather = weatherRemoteDataSource.getWeather(lat, lon)
        Timber.d("weather repo impl - remote weather: ${remoteWeather}")
        when (remoteWeather) {
            is DataResource.Error -> {
                return remoteWeather
            }

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