package com.todaylab.cleanarchtemplate.local

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

internal fun WeatherLocal.toData(): WeatherEntity {
    return WeatherEntity(
        cityName = "",
        temp = 0.0,
        weatherMain = this.main,
        weatherDesc = this.description,
        weatherIcon = this.icon,
    )
}

internal fun WeatherEntity.toLocal(): WeatherLocal {
    return WeatherLocal(
        id = 0,
        main = this.weatherMain,
        description = this.weatherDesc,
        icon = this.weatherIcon,
    )
}