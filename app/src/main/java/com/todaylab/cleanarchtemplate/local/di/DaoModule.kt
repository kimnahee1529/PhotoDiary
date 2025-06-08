package com.todaylab.cleanarchtemplate.local.di

import com.todaylab.cleanarchtemplate.local.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * local layer
 * di for room data access objects
 */
@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase): WeatherDao = database.weatherDao()
}