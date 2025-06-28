package com.todaylab.cleanarchtemplate.data.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.domain.model.Weather


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
            cityName = "",
            temp = 0.0,
            weatherMain = high.main,
            weatherDesc = high.description,
            weatherIcon = high.icon,
            timestamp = System.currentTimeMillis()
        )
    }
}
