package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.WeatherRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherByLocationUseCase
import javax.inject.Inject

class GetWeatherByLocationUseCaseImpl @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : GetWeatherByLocationUseCase {
    override suspend operator fun invoke(lat: Double, lon: Double): DataResource<Weather> {
        return weatherRepository.getByLocation(lat, lon)
    }
}