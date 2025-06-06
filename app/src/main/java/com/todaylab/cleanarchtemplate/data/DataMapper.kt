package com.todaylab.cleanarchtemplate.data

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * data layer
 * model mapper extension functions
 */

/**
 * weather model mapper
 * from data to domain
 */
internal fun WeatherEntity.toDomain(): Weather = Weather(
    lang = this.lang,
    lat = this.lat,
    date = this.date,
    summary = this.summary,
    main = this.main,
    description = this.description,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp
)

/**
 * weather model mapper
 * from domain to data
 */
internal fun Weather.toData(): WeatherEntity = WeatherEntity(
    lang = this.lang,
    lat = this.lat,
    date = this.date,
    summary = this.summary,
    main = this.main,
    description = this.description,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp
)