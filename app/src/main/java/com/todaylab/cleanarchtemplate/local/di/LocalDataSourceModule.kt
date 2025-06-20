package com.todaylab.cleanarchtemplate.local.di

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.local.impl.WeatherLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * local layer
 * di for local data source
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindWeatherLocalDataSource(
        weatherLocalDataSourceImpl: WeatherLocalDataSourceImpl
    ): WeatherLocalDataSource
}