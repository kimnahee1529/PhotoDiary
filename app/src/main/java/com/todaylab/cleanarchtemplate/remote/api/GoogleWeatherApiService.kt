package com.todaylab.cleanarchtemplate.remote.api

import com.todaylab.cleanarchtemplate.BuildConfig
import com.todaylab.cleanarchtemplate.remote.model.WeatherGoogleWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * remote layer
 * google weather api service interface
 */
interface GoogleWeatherApiService {
    @GET("currentConditions:lookup")
    suspend fun getCurrentWeather(
        @Query("key") apiKey: String = BuildConfig.GOOGLE_MAP_API_KEY,
        @Query("location.latitude") lat: Double,
        @Query("location.longitude") long: Double
    ): Response<WeatherGoogleWeatherResponse>
}