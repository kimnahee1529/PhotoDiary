package com.todaylab.cleanarchtemplate.data

import com.todaylab.cleanarchtemplate.data.model.BirthDateEntity
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * data layer
 * model mapper extension functions
 */
internal fun WeatherEntity.toDomain(): Weather {
    return Weather(
        date = this.date,
        lat = this.lat,
        lon = this.lon,
        main = this.weatherMain,
        description = this.weatherDesc,
        icon = this.weatherIcon,
    )
}

internal fun Weather.toData(): WeatherEntity {
    return WeatherEntity(
        date = this.date,
        lat = this.lat,
        lon = this.lon,
        cityName = "",
        temp = 0.0,
        weatherMain = this.main,
        weatherDesc = this.description,
        weatherIcon = this.icon,
        timestamp = System.currentTimeMillis()
    )
}

internal fun BirthDateEntity.toDomain(): BirthDate {
    return BirthDate(
        year = this.year,
        month = this.month,
        day = this.day,
    )
}

internal fun BirthDate.toData(): BirthDateEntity {
    return BirthDateEntity(
        year = this.year,
        month = this.month,
        day = this.day,
    )
}
