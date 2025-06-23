package com.todaylab.cleanarchtemplate.presentation.model

/**
 * HomeViewModel 이 갖는 상태를 담는 model 클래스
 */
data class HomeStateModel(
    val weather: WeatherModel? = null,
    val birthDate: BirthDateModel = BirthDateModel()
)