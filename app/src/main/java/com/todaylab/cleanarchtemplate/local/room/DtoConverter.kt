package com.todaylab.cleanarchtemplate.local.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.todaylab.cleanarchtemplate.domain.model.Color
import java.time.LocalDate
import java.util.Date

class DtoConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

    @TypeConverter
    fun fromCharList(charList: List<Char>?): String? {
        return charList?.joinToString("") // List<Char>를 하나의 String으로 합침
    }

    @TypeConverter
    fun toCharList(charString: String?): List<Char>? {
        return charString?.toList() // String을 다시 List<Char>로 변환
    }

    @TypeConverter
    fun fromColor(color: Color?): String? = color?.name

    @TypeConverter
    fun toColor(value: String?): Color? = value?.let { Color.valueOf(it) }


}
