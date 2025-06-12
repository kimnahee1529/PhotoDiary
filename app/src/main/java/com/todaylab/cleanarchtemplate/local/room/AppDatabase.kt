package com.todaylab.cleanarchtemplate.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todaylab.cleanarchtemplate.local.model.BirthdayLocal
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao

@Database(
    entities = [BirthdayLocal::class],
    version = RoomConstant.ROOM_VERSION
)

@TypeConverters(
    DtoConverter::class
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): WeatherDao

}