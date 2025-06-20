package com.todaylab.cleanarchtemplate.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todaylab.cleanarchtemplate.local.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

/**
 * local layer
 * room database
 */
@Database(entities = [WeatherLocal::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}