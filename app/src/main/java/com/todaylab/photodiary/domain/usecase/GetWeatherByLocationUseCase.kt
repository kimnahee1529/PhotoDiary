package com.todaylab.photodiary.domain.usecase

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.Weather

interface GetWeatherByLocationUseCase {
    suspend operator fun invoke(lat: Double, lon: Double): DataResource<Weather>
}