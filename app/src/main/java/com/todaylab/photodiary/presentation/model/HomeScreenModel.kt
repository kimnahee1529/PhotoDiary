package com.todaylab.photodiary.presentation.model

import com.todaylab.photodiary.core.DataResource

/**
 * HomeViewModel state model
 */
data class HomeScreenModel(
    val weather: DataResource<WeatherModel> = DataResource.Companion.loading(),
    val birthDate: DataResource<BirthDateModel> = DataResource.Companion.loading()
)