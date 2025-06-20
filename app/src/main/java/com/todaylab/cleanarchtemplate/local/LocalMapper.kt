package com.todaylab.cleanarchtemplate.local

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

/**
 * local layer
 * model mappers
 */

/**
 * weather model mapper
 * from local to data
 */
internal fun WeatherLocal.toData(): WeatherEntity = WeatherEntity(
    long = this.long,
    lat = this.lat,
    date = this.date,
    main = this.main,
    description = this.description,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp,
)

/**
 * weather model mapper
 * from data to local
 */
internal fun WeatherEntity.toLocal(): WeatherLocal = WeatherLocal(
    long = this.long,
    lat = this.lat,
    date = this.date,
    main = this.main,
    description = this.description,
    maxTemp = this.maxTemp,
    minTemp = this.minTemp,
)