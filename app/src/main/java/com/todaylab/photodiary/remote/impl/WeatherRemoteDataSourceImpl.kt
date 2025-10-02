package com.todaylab.photodiary.remote.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.model.WeatherEntity
import com.todaylab.photodiary.data.remote.WeatherRemoteDataSource
import com.todaylab.photodiary.remote.api.WeatherApiService
import com.todaylab.photodiary.remote.toData
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
                } ?: DataResource.loading()
            } else {
                throw Throwable(response.code().toString())
            }
        } catch (e: Exception) {
            DataResource.error(Throwable("remote layer error - ${e.message}"))
        }
    }
}
