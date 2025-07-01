package com.todaylab.cleanarchtemplate.domain.di

import com.todaylab.cleanarchtemplate.domain.impl.GetBirthDateUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.GetLuckyResultByIdUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.GetWeatherByLocationUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.impl.SaveBirthDateUseCaseImpl
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultByIdUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherByLocationUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
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
    abstract fun bindGetWeatherByLocationUseCase(impl: GetWeatherByLocationUseCaseImpl): GetWeatherByLocationUseCase

    @Binds
    @Singleton
    abstract fun bindSaveBirthDateUseCase(impl: SaveBirthDateUseCaseImpl): SaveBirthDateUseCase

    @Binds
    @Singleton
    abstract fun bindGetBirthDateUseCase(impl: GetBirthDateUseCaseImpl): GetBirthDateUseCase

    @Binds
    @Singleton
    abstract fun bindGetLuckyResultByIdUseCase(impl: GetLuckyResultByIdUseCaseImpl): GetLuckyResultByIdUseCase
}