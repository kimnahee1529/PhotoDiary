package com.todaylab.cleanarchtemplate.ui.model

data class HomeState(
    val weather: WeatherState? = null,
    val birthDate: BirthDateState = BirthDateState(),
)