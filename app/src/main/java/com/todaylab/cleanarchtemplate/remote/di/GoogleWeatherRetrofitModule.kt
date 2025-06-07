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
 * di for google weather api retrofit
 * @deprecated not used
 *
 * https://developers.google.com/maps/documentation/weather/current-conditions?
 */
@Module
@InstallIn(SingletonComponent::class)
object GoogleWeatherRetrofitModule {
    private const val BASE_URL = "https://weather.googleapis.com/"

    @Singleton
    @Provides
    @GoogleWeatherRetrofit
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
