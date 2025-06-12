package com.todaylab.cleanarchtemplate.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.cleanarchtemplate.local.model.BirthdayLocal
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(birthday: BirthdayLocal)

    @Query("SELECT * FROM user_weather WHERE userId = :userId")
    suspend fun getWeather(userId: String): WeatherLocal?
}
