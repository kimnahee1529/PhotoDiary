package com.todaylab.cleanarchtemplate.remote.di

import com.todaylab.cleanarchtemplate.remote.api.OpenWeatherApiService
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
object ApiServiceModule {
    /**
     * provide open weather api service
     */
    @Provides
    @Singleton
    fun provideOpenWeatherApiService(retrofit: Retrofit): OpenWeatherApiService =
        retrofit.create(OpenWeatherApiService::class.java)
}