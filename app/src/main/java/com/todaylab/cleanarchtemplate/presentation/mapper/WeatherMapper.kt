package com.todaylab.cleanarchtemplate.presentation.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel

/**
 * Weather mapper
 * - high level model: Weather (domain layer)
 * - low level model: WeatherModel (presentation layer)
 */
internal object WeatherMapper : BaseModelMapper<Weather, WeatherModel> {
    override fun mapToHigh(low: WeatherModel): Weather {
        return Weather(
            date = low.date,
            lat = low.lat,
            lon = low.lon,
            main = low.main,
            description = low.description,
            icon = low.icon,
        )
    }

    override fun mapToLow(high: Weather): WeatherModel {
        return WeatherModel(
            date = high.date,
            lat = high.lat,
            lon = high.lon,
            main = high.main,
            description = high.description,
            icon = high.icon,
        )
    }
}