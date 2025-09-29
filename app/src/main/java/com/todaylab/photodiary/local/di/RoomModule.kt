package com.todaylab.photodiary.local.di

import android.content.Context
import androidx.room.Room
import com.todaylab.photodiary.local.room.AppDatabase
import com.todaylab.photodiary.local.room.RoomConstant
import com.todaylab.photodiary.local.room.dao.DiaryDao
import com.todaylab.photodiary.local.room.dao.LuckyResultDao
import com.todaylab.photodiary.local.room.dao.WeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            RoomConstant.ROOM_DB_NAME
        )
            .fallbackToDestructiveMigration(false)
            .build()

    @Provides
    @Singleton
    fun provideWeatherDao(database: AppDatabase): WeatherDao = database.weatherDao()

    @Provides
    @Singleton
    fun provideLuckyResultDao(database: AppDatabase): LuckyResultDao = database.luckyDao()

    @Provides
    @Singleton
    fun provideDiaryDao(database: AppDatabase): DiaryDao = database.diaryDao()

}