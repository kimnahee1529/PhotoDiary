package com.todaylab.cleanarchtemplate.remote.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * remote layer
 * di for open weather api retrofit
 *
 * https://openweathermap.org/api/one-call-3#current
 */
@Module
@InstallIn(SingletonComponent::class)
object OpenWeatherRetrofitModule {
    private const val BASE_URL = "https://api.openweathermap.org/data/3.0/"

    @Singleton
    @Provides
    @OpenWeatherRetrofit
    fun provideRetrofit(
        httpClient: OkHttpClient
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(httpClient)
            .build()
}

