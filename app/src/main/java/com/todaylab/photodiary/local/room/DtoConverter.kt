package com.todaylab.photodiary.local.room

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import com.todaylab.photodiary.domain.model.Color
import com.todaylab.photodiary.domain.model.WeatherType
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.util.Date

class DtoConverter {

    @TypeConverter
    fun fromTimestamp(value: Long): Date = Date(value)

    @TypeConverter
    fun dateToTimestamp(date: Date): Long = date.time

//    @TypeConverter
//    fun fromLocalDate(date: LocalDate?): String? = date?.toString()
//
//    @TypeConverter
//    fun toLocalDate(value: String?): LocalDate? = value?.let { LocalDate.parse(it) }

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


    // ----- LocalDate -----
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? = value?.toString()

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? = value?.let(LocalDate::parse)

    // ----- LocalTime -----
    @TypeConverter
    fun fromLocalTime(value: LocalTime?): String? = value?.toString()

    @TypeConverter
    fun toLocalTime(value: String?): LocalTime? = value?.let(LocalTime::parse)

    // ----- WeatherType(enum) -----
    @TypeConverter
    fun fromWeatherType(value: WeatherType?): String? = value?.name

    @TypeConverter
    fun toWeatherType(value: String?): WeatherType? =
        value?.let { WeatherType.valueOf(it) }

    // ----- List<String> (JSON으로 저장) -----
    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? =
        list?.let { json.encodeToString(ListSerializer(String.serializer()), it) }

    @TypeConverter
    fun toStringList(value: String?): List<String> =
        value?.let { json.decodeFromString(ListSerializer(String.serializer()), it) }
            ?: emptyList()
}
