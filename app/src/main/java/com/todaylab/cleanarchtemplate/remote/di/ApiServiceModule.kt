package com.todaylab.cleanarchtemplate.remote.di

import com.todaylab.cleanarchtemplate.remote.api.GoogleWeatherApiService
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
     * provide google weather api service
     * @deprecated not used
     */
    @Provides
    @Singleton
    fun provideGoogleWeatherApiService(@GoogleWeatherRetrofit retrofit: Retrofit): GoogleWeatherApiService =
        retrofit.create(GoogleWeatherApiService::class.java)

    /**
     * provide open weather api service
     */
    @Provides
    @Singleton
    fun provideOpenWeatherApiService(@OpenWeatherRetrofit retrofit: Retrofit): OpenWeatherApiService =
        retrofit.create(OpenWeatherApiService::class.java)
}