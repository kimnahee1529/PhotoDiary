package com.todaylab.cleanarchtemplate.remote.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.remote.api.WeatherApiService
import com.todaylab.cleanarchtemplate.remote.toData
import timber.log.Timber
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double): DataResource<WeatherEntity> {
        try {
            val response = api.getWeather(lat, lon)
            Timber.d("weather remote impl - response: $response")

            if (response.isSuccessful) {
                val body = response.body() ?: throw Exception("remote layer error - empty body")
                return DataResource.success(body.toData())
            } else {
                throw Exception("remote layer error - API error: ${response.code()}")
            }
        } catch (e: Exception) {
            return DataResource.error(Throwable(e))
        }
    }
}
