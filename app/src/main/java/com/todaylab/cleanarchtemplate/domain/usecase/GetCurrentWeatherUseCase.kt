package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import javax.inject.Inject

/**
 * domain layer
 * get current weather use case
 */
class GetCurrentWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository
) {
    suspend operator fun invoke(lat: Double, long: Double) =
        weatherRepository.getCurrentWeather(lat, long)
}