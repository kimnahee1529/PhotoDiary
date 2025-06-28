package com.todaylab.cleanarchtemplate.presentation.model

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * HomeViewModel state model
 */
data class HomeScreenModel(
    val weather: DataResource<WeatherModel> = DataResource.Companion.loading(),
    val birthDate: DataResource<BirthDateModel> = DataResource.Companion.loading()
)