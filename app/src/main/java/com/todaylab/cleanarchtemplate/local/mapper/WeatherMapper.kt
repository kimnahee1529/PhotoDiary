package com.todaylab.cleanarchtemplate.local.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

/**
 * Weather mapper
 * - high level model: WeatherEntity (data layer)
 * - low level model: WeatherLocal (local layer)
 */
internal object WeatherMapper : BaseModelMapper<WeatherEntity, WeatherLocal> {
    override fun mapToHigh(low: WeatherLocal): WeatherEntity {
        return WeatherEntity(
            date = low.date,
            lat = low.lat,
            lon = low.lon,
            weatherMain = low.main,
            weatherDesc = low.description,
            weatherIcon = low.icon,
        )
    }

    override fun mapToLow(high: WeatherEntity): WeatherLocal {
        return WeatherLocal(
            date = high.date,
            lat = high.lat,
            lon = high.lon,
            main = high.weatherMain,
            description = high.weatherDesc,
            icon = high.weatherIcon,
        )
    }
}