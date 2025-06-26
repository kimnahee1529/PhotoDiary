package com.todaylab.cleanarchtemplate.presentation.model

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * HomeViewModel state model
 */
data class HomeStateModel(
    val weather: DataResource<WeatherModel> = DataResource.loading(),
    val birthDate: DataResource<BirthDateModel> = DataResource.loading()
)