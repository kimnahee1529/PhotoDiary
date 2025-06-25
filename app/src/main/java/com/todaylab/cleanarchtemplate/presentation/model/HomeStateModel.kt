package com.todaylab.cleanarchtemplate.presentation.model

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * HomeViewModel 이 갖는 상태를 담는 model 클래스
 */
data class HomeStateModel(
    val weather: DataResource<WeatherModel> = DataResource.loading(),
    val birthDate: BirthDateModel = BirthDateModel()
)