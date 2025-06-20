package com.todaylab.cleanarchtemplate.local.room

import androidx.room.TypeConverter
import java.util.Date

class DtoConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

}
