package com.todaylab.cleanarchtemplate.local.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.cleanarchtemplate.local.model.WEATHER_ID
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal
import com.todaylab.cleanarchtemplate.local.room.RoomConstant

/**
 * only one weather entity exists at a time, pk = 0 (singleton)
 */
@Dao
interface WeatherDao {
    /**
     * get singleton weather entity, if exists
     */
    @Query("SELECT * " +
                "FROM ${RoomConstant.TABLE.USER_WEATHER} " +
                "WHERE id= $WEATHER_ID "
    )
    suspend fun getWeather(): WeatherLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveWeather(weather: WeatherLocal)

    @Query(
        "DELETE " +
                "FROM ${RoomConstant.TABLE.USER_WEATHER} " +
                "WHERE id= $WEATHER_ID "
    )
    suspend fun deleteWeather()
}