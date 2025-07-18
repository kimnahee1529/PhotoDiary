package com.todaylab.cleanarchtemplate.ui.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
import com.todaylab.cleanarchtemplate.ui.model.WeatherState

/**
 * Weather mapper
 * - high level model: WeatherModel (presentation layer)
 * - low level model: WeatherState (ui layer)
 */
internal object WeatherMapper : BaseModelMapper<WeatherModel, WeatherState> {
    override fun mapToHigh(low: WeatherState): WeatherModel {
        return WeatherModel(
            date = low.date,
            lat = low.lat,
            lon = low.lon,
            main = low.main,
            description = low.description,
            icon = low.icon,
        )
    }

    override fun mapToLow(high: WeatherModel): WeatherState {
        return WeatherState(
            date = high.date,
            lat = high.lat,
            lon = high.lon,
            main = high.main,
            description = high.description,
            iconLabel = WeatherState.getIconLabel(high.icon),
            icon = high.icon,
        )
    }
}