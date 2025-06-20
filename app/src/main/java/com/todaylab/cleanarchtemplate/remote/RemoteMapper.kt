package com.todaylab.cleanarchtemplate.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.remote.model.response.WeatherResponse

internal fun WeatherResponse.toData(): WeatherEntity {
    return WeatherEntity(
        cityName = this.name,
        temp = this.main.temp,
        weatherMain = this.weather.firstOrNull()?.main ?: "Unknown",
        weatherDesc = this.weather.firstOrNull()?.description ?: "No description",
        weatherIcon = this.weather.firstOrNull()?.icon ?: "Unknown",
    )
}