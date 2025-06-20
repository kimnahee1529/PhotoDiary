package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import javax.inject.Inject

interface GetWeatherUseCase{
    suspend operator fun invoke(lat: Double, lon: Double): Weather
}