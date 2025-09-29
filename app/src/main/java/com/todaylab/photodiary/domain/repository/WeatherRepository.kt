package com.todaylab.photodiary.domain.repository

import BaseSingletonRepository
import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.Weather

/**
 * domain weather repository
 * - 좌표에 따른 오늘 날씨 반환
 */
interface WeatherRepository : BaseSingletonRepository<Weather> {
    suspend fun getByLocation(lat: Double, lon: Double): DataResource<Weather>
}