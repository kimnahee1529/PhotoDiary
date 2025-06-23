package com.todaylab.cleanarchtemplate.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.todaylab.cleanarchtemplate.BuildConfig
import com.todaylab.cleanarchtemplate.remote.api.interceptor.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * remote layer
 * di for open weather api retrofit
 */
@Module
@InstallIn(SingletonComponent::class)
object WeatherRetrofitModule {
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private const val APP_KEY = BuildConfig.WEATHER_API_KEY

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        val httpClient = OkHttpClient
            .Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(ApiKeyInterceptor("appid", APP_KEY))
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
    }
}