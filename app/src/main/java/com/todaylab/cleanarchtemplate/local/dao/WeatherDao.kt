package com.todaylab.cleanarchtemplate.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.cleanarchtemplate.local.model.WEATHER_ID
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

/**
 * local layer
 * room db data access object
 *
 * only one weather entity exists at a time, pk = 0 (singleton)
 */
@Dao
interface WeatherDao {
    /**
     * get singleton weather entity, if exists
     */
    @Query(
        "SELECT * " +
                "FROM WeatherLocal " +
                "WHERE id= $WEATHER_ID "
    )
    fun getWeather(): WeatherLocal?

    /**
     * save weather entity
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(weather: WeatherLocal)

    /**
     * delete weather entity
     */
    @Query(
        "DELETE " +
                "FROM WeatherLocal " +
                "WHERE id= $WEATHER_ID "
    )
    fun deleteWeather()
}