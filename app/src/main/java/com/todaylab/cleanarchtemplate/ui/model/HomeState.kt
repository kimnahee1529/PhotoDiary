package com.todaylab.cleanarchtemplate.ui.model

import com.todaylab.cleanarchtemplate.core.DataResource

data class HomeState(
    val weather: DataResource<WeatherState> = DataResource.loading(),
    val birthDate: DataResource<BirthDateState> = DataResource.loading(),
)