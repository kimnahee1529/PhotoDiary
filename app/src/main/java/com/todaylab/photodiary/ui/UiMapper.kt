package com.todaylab.photodiary.ui

import com.todaylab.photodiary.presentation.model.HomeScreenModel
import com.todaylab.photodiary.ui.mapper.BirthDateMapper
import com.todaylab.photodiary.ui.mapper.WeatherMapper
import com.todaylab.photodiary.ui.model.HomeScreenState

internal fun HomeScreenModel.toUi() = HomeScreenState(
    weather = weather.mapData(WeatherMapper::mapToLow),
    birthDate = birthDate.mapData(BirthDateMapper::mapToLow),
)
