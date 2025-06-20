package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase
import javax.inject.Inject

class GetWeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
): GetWeatherUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double): Weather {
        return weatherRepository.getWeather(lat, lon)
    }
}