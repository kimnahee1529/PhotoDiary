package com.todaylab.cleanarchtemplate.presentation.weather

import com.todaylab.cleanarchtemplate.domain.model.Weather

data class WeatherUiState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val errorMessage: String? = null
)