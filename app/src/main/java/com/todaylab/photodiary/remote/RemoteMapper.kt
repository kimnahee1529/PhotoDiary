package com.todaylab.photodiary.remote

import com.todaylab.photodiary.data.model.WeatherEntity
import com.todaylab.photodiary.remote.model.response.WeatherResponse
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