package com.todaylab.photodiary.ui.model

import com.todaylab.photodiary.core.DataResource

data class HomeScreenState(
    val weather: DataResource<WeatherState> = DataResource.loading(),
    val birthDate: DataResource<BirthDateState> = DataResource.loading(),
)