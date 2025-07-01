package com.todaylab.cleanarchtemplate.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.remote.model.response.WeatherResponse
import java.util.Date

internal fun WeatherResponse.toData(): WeatherEntity {
    return WeatherEntity(
        date = Date(), // set current date
        lat = this.coord.lat,
        lon = this.coord.lon,
        weatherMain = this.weather.firstOrNull()?.main ?: "Unknown",
        weatherDesc = this.weather.firstOrNull()?.description ?: "No description",
        weatherIcon = this.weather.firstOrNull()?.icon ?: "Unknown",
    )
}