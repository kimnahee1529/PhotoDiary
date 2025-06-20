package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import com.todaylab.cleanarchtemplate.domain.usecase.SaveWeatherUseCase
import javax.inject.Inject

class SaveWeatherUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
): SaveWeatherUseCase {
    override suspend operator fun invoke(weather: Weather) {
        return weatherRepository.saveWeather(weather)
    }

}