package com.todaylab.cleanarchtemplate.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.remote.model.WeatherGoogleWeatherResponse
import java.util.Date

/**
 * remote layer
 * model mappers
 */

/**
 * google weather api response to data
 * @deprecated not used
 */
fun WeatherGoogleWeatherResponse.toData(lang: Double, lat: Double): WeatherEntity = WeatherEntity(
    lang = lang,
    lat = lat,
    date = Date(),
    main = this.weatherCondition.type,
    description = this.weatherCondition.description.text,
    maxTemp = this.currentConditionsHistory.maxTemperature.degrees,
    minTemp = this.currentConditionsHistory.minTemperature.degrees
)

/**
 * open weather api response to data
 */
//fun WeatherOpenWeatherResponse.toData(): WeatherEntity = WeatherEntity()