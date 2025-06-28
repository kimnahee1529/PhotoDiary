package com.todaylab.cleanarchtemplate.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.todaylab.cleanarchtemplate.local.room.RoomConstant
import java.util.Date

// id of singleton weather local model
const val WEATHER_ID = 0

/**
 * local weather model
 * room db entity
 */
@Entity(tableName = RoomConstant.TABLE.USER_WEATHER)
data class WeatherLocal(
    @PrimaryKey val id: Int = WEATHER_ID,
    val date: Date,
    val lat: Double,
    val main: String,
    val lon: Double,
    val description: String,
    val icon: String,
)