package com.todaylab.photodiary.local.di

import com.todaylab.photodiary.data.local.DiaryLocalDataSource
import com.todaylab.photodiary.data.local.LuckyResultLocalDataSource
import com.todaylab.photodiary.data.local.MagicBookLocalDataSource
import com.todaylab.photodiary.data.local.WeatherLocalDataSource
import com.todaylab.photodiary.local.impl.DiaryLocalDataSourceImpl
import com.todaylab.photodiary.local.impl.LuckyResultLocalDataSourceImpl
import com.todaylab.photodiary.local.impl.MagicBookLocalDataSourceImpl
import com.todaylab.photodiary.local.impl.WeatherLocalDataSourceImpl
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
    abstract fun bindLuckyResultLocalDataSource(impl: LuckyResultLocalDataSourceImpl): LuckyResultLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDiaryLocalDataSource(impl: DiaryLocalDataSourceImpl): DiaryLocalDataSource

    @Binds
    @Singleton
    abstract fun bindMagicBookLocalDataSource(impl: MagicBookLocalDataSourceImpl): MagicBookLocalDataSource

}