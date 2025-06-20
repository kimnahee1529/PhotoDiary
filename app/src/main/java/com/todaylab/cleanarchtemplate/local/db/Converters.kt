package com.todaylab.cleanarchtemplate.local.db

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

/**
 * local layer
 * type converters for room database
 *
 * https://developer.android.com/training/data-storage/room/referencing-data
 */
class Converters {
    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    @TypeConverter
    fun fromTimestamp(value: String): Date {
        return simpleDateFormat.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date): String {
        return simpleDateFormat.format(date)
    }
}