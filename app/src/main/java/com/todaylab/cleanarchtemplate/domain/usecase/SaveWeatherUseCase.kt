package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.model.Weather

interface SaveWeatherUseCase {
    suspend operator fun invoke(weather: Weather)
}