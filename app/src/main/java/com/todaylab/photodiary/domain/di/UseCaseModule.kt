package com.todaylab.photodiary.domain.di

import com.todaylab.photodiary.domain.impl.GetDiaryListUseCaseImpl
import com.todaylab.photodiary.domain.impl.GetDiaryUseCaseImpl
import com.todaylab.photodiary.domain.impl.GetLuckyResultByIdUseCaseImpl
import com.todaylab.photodiary.domain.impl.GetSolutionByMagicBookUseCaseImpl
import com.todaylab.photodiary.domain.impl.GetWeatherByLocationUseCaseImpl
import com.todaylab.photodiary.domain.impl.SaveDiaryUseCaseImpl
import com.todaylab.photodiary.domain.usecase.GetDiaryListUseCase
import com.todaylab.photodiary.domain.usecase.GetDiaryUseCase
import com.todaylab.photodiary.domain.usecase.GetLuckyResultByIdUseCase
import com.todaylab.photodiary.domain.usecase.GetSolutionByMagicBookUseCase
import com.todaylab.photodiary.domain.usecase.GetWeatherByLocationUseCase
import com.todaylab.photodiary.domain.usecase.SaveDiaryUseCase
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
    abstract fun bindGetLuckyResultByIdUseCase(impl: GetLuckyResultByIdUseCaseImpl): GetLuckyResultByIdUseCase

    @Binds
    @Singleton
    abstract fun bindSaveDiaryUseCase(impl: SaveDiaryUseCaseImpl): SaveDiaryUseCase

    @Binds
    @Singleton
    abstract fun bindGetDiaryListUseCase(impl: GetDiaryListUseCaseImpl): GetDiaryListUseCase

    @Binds
    @Singleton
    abstract fun bindGetDiaryUseCase(impl: GetDiaryUseCaseImpl): GetDiaryUseCase

    @Binds
    @Singleton
    abstract fun bindGetSolutionUseCase(impl: GetSolutionByMagicBookUseCaseImpl): GetSolutionByMagicBookUseCase
}