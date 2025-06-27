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
     * get singleton weather entity, where location matches
     */
    @Query(
        "SELECT * " +
                "FROM ${RoomConstant.TABLE.USER_WEATHER} " +
                "WHERE id= $WEATHER_ID AND lat = :lat AND lon = :lon "
    )
    fun getWeather(lat: Double, lon: Double): WeatherLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveWeather(weather: WeatherLocal)

    @Query(
        "DELETE " +
                "FROM ${RoomConstant.TABLE.USER_WEATHER} " +
                "WHERE id= $WEATHER_ID "
    )
    fun deleteWeather()
}