package com.todaylab.photodiary.data.di

import com.todaylab.photodiary.data.impl.DiaryRepositoryImpl
import com.todaylab.photodiary.data.impl.LuckyResultRepositoryImpl
import com.todaylab.photodiary.data.impl.MagicBookRepositoryImpl
import com.todaylab.photodiary.data.impl.WeatherRepositoryImpl
import com.todaylab.photodiary.domain.repository.DiaryRepository
import com.todaylab.photodiary.domain.repository.LuckyResultRepository
import com.todaylab.photodiary.domain.repository.MagicBookRepository
import com.todaylab.photodiary.domain.repository.WeatherRepository
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
    abstract fun bindLuckyResultRepository(impl: LuckyResultRepositoryImpl): LuckyResultRepository

    @Binds
    @Singleton
    abstract fun bindDiaryRepository(impl: DiaryRepositoryImpl): DiaryRepository

    @Binds
    @Singleton
    abstract fun bindMagicBookRepository(impl: MagicBookRepositoryImpl): MagicBookRepository

}