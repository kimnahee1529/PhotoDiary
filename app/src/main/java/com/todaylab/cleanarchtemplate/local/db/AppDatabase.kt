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

// todo: fix build warning
// Schema export directory was not provided to the annotation processor so Room cannot export the schema. You can either provide `room.schemaLocation` annotation processor argument by applying the Room Gradle plugin (id 'androidx.room') OR set exportSchema to false.