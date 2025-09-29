package com.todaylab.photodiary.ui.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.presentation.model.WeatherModel
import com.todaylab.photodiary.ui.model.WeatherState

/**
 * Weather mapper
 * - high level model: WeatherModel (presentation layer)
 * - low level model: WeatherState (ui layer)
 */
internal object WeatherMapper {
    //ui에서 presentation으로 갈 필요는 없을 것 같은데???
//    override fun mapToHigh(low: WeatherState): WeatherModel {
//        return WeatherModel(
//            date = low.date,
//            lat = low.lat,
//            lon = low.lon,
//            main = low.main,
//            description = low.description,
//            icon = low.icon,
//        )
//    }

    fun mapToLow(high: WeatherModel): WeatherState {
        return WeatherState(
            state = WeatherState.getIconLabel(high.icon)
        )
    }
}
fun WeatherModel.toState(): WeatherState {
    return WeatherState(
        state = WeatherState.getIconLabel(this.icon)
    )
}