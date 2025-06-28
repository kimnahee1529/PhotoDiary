package com.todaylab.cleanarchtemplate.ui

import com.todaylab.cleanarchtemplate.presentation.model.HomeScreenModel
import com.todaylab.cleanarchtemplate.ui.mapper.BirthDateMapper
import com.todaylab.cleanarchtemplate.ui.mapper.WeatherMapper
import com.todaylab.cleanarchtemplate.ui.model.HomeScreenState

internal fun HomeScreenModel.toUi() = HomeScreenState(
    weather = weather.mapData(WeatherMapper::mapToLow),
    birthDate = birthDate.mapData(BirthDateMapper::mapToLow),
)
