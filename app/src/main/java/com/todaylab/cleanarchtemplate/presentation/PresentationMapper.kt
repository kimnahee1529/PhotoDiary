package com.todaylab.cleanarchtemplate.presentation

import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel

internal fun Weather.toPresentation() = WeatherModel(
    date = date,
    lat = lat,
    lon = lon,
    main = main,
    description = description,
    icon = icon,
)

internal fun BirthDate.toPresentation() = BirthDateModel(
    year = year,
    month = month,
    day = day,
)


internal fun BirthDateModel.toDomain() = BirthDate(
    year = year,
    month = month,
    day = day,
)