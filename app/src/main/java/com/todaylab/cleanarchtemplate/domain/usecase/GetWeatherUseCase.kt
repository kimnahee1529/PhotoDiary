package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.Weather

interface GetWeatherUseCase{
    suspend operator fun invoke(lat: Double, lon: Double): DataResource<Weather>
}