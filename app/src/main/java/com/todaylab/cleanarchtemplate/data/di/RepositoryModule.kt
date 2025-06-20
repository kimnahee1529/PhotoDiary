package com.todaylab.cleanarchtemplate.data.di

import com.todaylab.cleanarchtemplate.data.impl.BirthDateRepositoryImpl
import com.todaylab.cleanarchtemplate.data.impl.WeatherRepositoryImpl
import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindBirthDateRepository(impl: BirthDateRepositoryImpl): BirthDateRepository
}