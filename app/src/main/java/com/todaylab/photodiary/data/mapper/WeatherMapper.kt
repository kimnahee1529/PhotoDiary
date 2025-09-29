package com.todaylab.photodiary.data.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.WeatherEntity
import com.todaylab.photodiary.domain.model.Weather


/**
 * Weather mapper
 * - high level model: Weather (domain layer)
 * - low level model: WeatherEntity (data layer)
 */
internal object WeatherMapper : BaseModelMapper<Weather, WeatherEntity> {
    override fun mapToHigh(low: WeatherEntity): Weather {
        return Weather(
            date = low.date,
            lat = low.lat,
            lon = low.lon,
            main = low.weatherMain,
            description = low.weatherDesc,
            icon = low.weatherIcon,
        )
    }

    override fun mapToLow(high: Weather): WeatherEntity {
        return WeatherEntity(
            date = high.date,
            lat = high.lat,
            lon = high.lon,
            weatherMain = high.main,
            weatherDesc = high.description,
            weatherIcon = high.icon,
        )
    }
}
