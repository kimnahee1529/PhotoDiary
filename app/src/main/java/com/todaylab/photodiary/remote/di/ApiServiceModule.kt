package com.todaylab.photodiary.remote.di

import com.todaylab.photodiary.remote.api.WeatherApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * remote layer
 * di for remote api services
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule{
    @Provides
    @Singleton
    fun provideWeatherApiService(retrofit: Retrofit): WeatherApiService {
        return retrofit.create(WeatherApiService::class.java)
    }
}