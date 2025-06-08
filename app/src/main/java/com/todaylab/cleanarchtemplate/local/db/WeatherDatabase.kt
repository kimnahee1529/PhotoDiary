package com.todaylab.cleanarchtemplate.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todaylab.cleanarchtemplate.local.dao.WeatherDao
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

/**
 * local layer
 * room database
 */
@Database(entities = [WeatherLocal::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}