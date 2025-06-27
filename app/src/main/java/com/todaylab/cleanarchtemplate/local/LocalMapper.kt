package com.todaylab.cleanarchtemplate.local

import com.todaylab.cleanarchtemplate.data.model.LuckyResultEntity
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity
import com.todaylab.cleanarchtemplate.local.model.LuckyResultLocal
import com.todaylab.cleanarchtemplate.local.model.WeatherLocal

internal fun WeatherLocal.toData(): WeatherEntity {
    return WeatherEntity(
        date = this.date,
        lat = this.lat,
        lon = this.lon,
        cityName = "",
        temp = 0.0,
        weatherMain = this.main,
        weatherDesc = this.description,
        weatherIcon = this.icon,
        timestamp = this.timestamp
    )
}

internal fun WeatherEntity.toLocal(): WeatherLocal {
    return WeatherLocal(
        date = this.date,
        lat = this.lat,
        lon = this.lon,
        main = this.weatherMain,
        description = this.weatherDesc,
        icon = this.weatherIcon,
        timestamp = System.currentTimeMillis()

    )
}

internal fun LuckyResultLocal.toData(): LuckyResultEntity {
    return LuckyResultEntity(
        id = this.id,
        date = this.date,
        animal = this.animal,
        numbers = this.numbers,
        initials = this.initials,
        color = this.color,
    )
}

internal fun LuckyResultEntity.toLocal(): LuckyResultLocal {
    return LuckyResultLocal(
        id = this.id,
        date = this.date,
        animal = this.animal,
        numbers = this.numbers,
        initials = this.initials,
        color = this.color,
    )
}