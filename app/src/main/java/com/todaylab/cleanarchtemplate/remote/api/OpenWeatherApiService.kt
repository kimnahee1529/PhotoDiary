package com.todaylab.cleanarchtemplate.remote.api

import com.todaylab.cleanarchtemplate.BuildConfig
import com.todaylab.cleanarchtemplate.remote.model.WeatherOpenWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * remote layer
 * open weather api service interface
 */
interface OpenWeatherApiService {
    /**
     * call onecall api to get daily weather forecast
     */
    @GET("onecall")
    suspend fun getCurrentWeather(
        @Query("appid") apiKey: String = BuildConfig.OPEN_WEATHER_API_KEY,
        @Query("lat") lat: Double,
        @Query("lon") long: Double,
        @Query("exclude") exclude: String = "current,minutely,hourly,alerts"
    ): Response<WeatherOpenWeatherResponse>
}
