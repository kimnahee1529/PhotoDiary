package com.todaylab.cleanarchtemplate.local.di

import com.todaylab.cleanarchtemplate.data.local.BirthDateLocalDataSource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.local.impl.BirthDateLocalDataSourceImpl
import com.todaylab.cleanarchtemplate.local.impl.WeatherLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindWeatherLocalDataSource(impl: WeatherLocalDataSourceImpl): WeatherLocalDataSource

    @Binds
    @Singleton
    abstract fun bindBirthDateLocalDataSource(impl: BirthDateLocalDataSourceImpl): BirthDateLocalDataSource

}