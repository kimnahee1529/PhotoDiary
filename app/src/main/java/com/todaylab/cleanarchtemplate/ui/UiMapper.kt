package com.todaylab.cleanarchtemplate.ui

import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeScreenModel
import com.todaylab.cleanarchtemplate.ui.mapper.WeatherMapper
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeScreenState

internal fun BirthDateModel.toUi() = BirthDateState(
    year = year,
    month = month,
    day = day,
)

internal fun HomeScreenModel.toUi() = HomeScreenState(
    weather = weather.mapData(WeatherMapper::mapToLow),
    birthDate = birthDate.mapData { it.toUi() },
)
