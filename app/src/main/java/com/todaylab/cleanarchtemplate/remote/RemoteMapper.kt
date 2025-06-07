package com.todaylab.cleanarchtemplate.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.remote.model.WeatherGoogleWeatherResponse
import com.todaylab.cleanarchtemplate.remote.model.WeatherOpenWeatherResponse
import java.util.Date

/**
 * remote layer
 * model mappers
 */

/**
 * google weather api response to data
 * @deprecated not used
 */
fun WeatherGoogleWeatherResponse.toData(long: Double, lat: Double): WeatherEntity = WeatherEntity(
    long = long,
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
fun WeatherOpenWeatherResponse.toData(long: Double, lat: Double): WeatherEntity = WeatherEntity(
    long = long,
    lat = lat,
    date = Date(),
    main = this.current.weather.first().main,
    description = this.current.weather.first().description,
    maxTemp = this.daily.first().temp.max,
    minTemp = this.daily.first().temp.min
)