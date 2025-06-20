package com.todaylab.cleanarchtemplate.domain.di

import com.todaylab.cleanarchtemplate.data.impl.WeatherRepositoryImpl
import com.todaylab.cleanarchtemplate.domain.impl.GetBirthDateUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.GetWeatherUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.SaveBirthDateUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.SaveWeatherUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveWeatherUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindGetWeatherUseCase(impl: GetWeatherUseCaseImpl): GetWeatherUseCase

    @Binds
    @Singleton
    abstract fun bindSaveWeatherUseCase(impl: SaveWeatherUseCaseImpl): SaveWeatherUseCase

    @Binds
    @Singleton
    abstract fun bindSaveBirthDateUseCase(impl: SaveBirthDateUseCaseImpl): SaveBirthDateUseCase

    @Binds
    @Singleton
    abstract fun bindGetBirthDateUseCase(impl: GetBirthDateUseCaseImpl): GetBirthDateUseCase


}