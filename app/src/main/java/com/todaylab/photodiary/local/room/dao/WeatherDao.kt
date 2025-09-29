package com.todaylab.photodiary.local.room.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.todaylab.photodiary.local.model.WEATHER_ID
import com.todaylab.photodiary.local.model.WeatherLocal
import com.todaylab.photodiary.local.room.RoomConstant

/**
 * only one weather entity exists at a time, pk = 0 (singleton)
 */
@Dao
interface WeatherDao {
    /**
     * get singleton weather entity
     */
    @Query(
        "SELECT * FROM ${RoomConstant.TABLE.USER_WEATHER} WHERE id= $WEATHER_ID"
    )
    suspend fun get(): WeatherLocal?

    /**
     * get singleton weather entity, where location matches
     */
    @Query(
        "SELECT * FROM ${RoomConstant.TABLE.USER_WEATHER} WHERE id= $WEATHER_ID AND lat = :lat AND lon = :lon"
    )
    suspend fun getByLocation(lat: Double, lon: Double): WeatherLocal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(item: WeatherLocal)

    @Query(
        "DELETE FROM ${RoomConstant.TABLE.USER_WEATHER} WHERE id= $WEATHER_ID "
    )
    suspend fun delete()
}