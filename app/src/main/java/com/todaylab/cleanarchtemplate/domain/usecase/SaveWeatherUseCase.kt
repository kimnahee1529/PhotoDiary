package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(weather: Weather) {
        return weatherRepository.saveWeather(weather)
    }

}