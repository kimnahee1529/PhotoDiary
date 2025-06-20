package com.todaylab.cleanarchtemplate.remote.api

import com.todaylab.cleanarchtemplate.remote.model.response.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat: Double = 37.5502596,
        @Query("lon") lon: Double = 127.073139,
    ): Response<WeatherResponse>
}
