package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.Weather
import com.todaylab.photodiary.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherByLocationUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
) {
    suspend operator fun invoke(lat: Double, lon: Double): DataResource<Weather> {
        return weatherRepository.getByLocation(lat, lon)
    }
}