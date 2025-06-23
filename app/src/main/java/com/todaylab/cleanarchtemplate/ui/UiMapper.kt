package com.todaylab.cleanarchtemplate.ui

import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeStateModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeState
import com.todaylab.cleanarchtemplate.ui.model.WeatherState

internal fun WeatherModel.toUi() = WeatherState(
    date = date,
    lat = lat,
    lon = lon,
    main = main,
    description = description,
    icon = icon,

    isLoading = isLoading,
    errorMessage = errorMessage
)

internal fun BirthDateModel.toUi() = BirthDateState(
    year = year,
    month = month,
    day = day,
    isLoading = isLoading,
    errorMessage = errorMessage
)

internal fun HomeStateModel.toUi() = HomeState(
    weather = weather?.toUi(),
    birthDate = birthDate.toUi()
)
