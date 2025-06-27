package com.todaylab.cleanarchtemplate.remote.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.remote.api.WeatherApiService
import com.todaylab.cleanarchtemplate.remote.toData
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(
    private val api: WeatherApiService
) : WeatherRemoteDataSource {

    override suspend fun getWeather(lat: Double, lon: Double): DataResource<WeatherEntity> {
        return try {
            val response = api.getWeather(lat, lon)

            if (response.isSuccessful) {
                response.body()?.let { body ->
                    DataResource.success(body.toData())
                } ?: DataResource.empty()
            } else {
                throw Throwable(response.code().toString())
            }
        } catch (e: Exception) {
            DataResource.error(Throwable("remote layer error - ${e.message}"))
        }
    }
}
