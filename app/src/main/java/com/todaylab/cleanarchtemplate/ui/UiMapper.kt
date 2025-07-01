package com.todaylab.cleanarchtemplate.ui

import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeStateModel
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeState
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState
import com.todaylab.cleanarchtemplate.ui.model.WeatherState

internal fun WeatherModel.toUi() = WeatherState(
    date = date,
    lat = lat,
    lon = lon,
    main = main,
    description = description,
    icon = icon,
)

internal fun BirthDateModel.toUi() = BirthDateState(
    year = year,
    month = month,
    day = day,
)

internal fun HomeStateModel.toUi() = HomeState(
    weather = weather.mapData { it.toUi() },
    birthDate = birthDate.mapData { it.toUi() },
)

internal fun LuckyResultModel.toUi() = LuckyResultState(
    id = id,
    date = date,
    animal = animal,
    numbers = numbers,
    initials = initials,
    color = color
)
