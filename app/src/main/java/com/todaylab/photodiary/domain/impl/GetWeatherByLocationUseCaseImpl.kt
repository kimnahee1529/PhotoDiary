package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.Weather
import com.todaylab.photodiary.domain.repository.WeatherRepository
import com.todaylab.photodiary.domain.usecase.GetWeatherByLocationUseCase
import javax.inject.Inject

class GetWeatherByLocationUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetWeatherByLocationUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double): DataResource<Weather> {
        return weatherRepository.getByLocation(lat, lon)
    }
}