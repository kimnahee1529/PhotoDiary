package com.todaylab.cleanarchtemplate.domain.repository

import BaseSingletonRepository
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * domain weather repository
 * - 좌표에 따른 오늘 날씨 반환
 */
interface WeatherRepository : BaseSingletonRepository<Weather> {
    suspend fun getByLocation(lat: Double, lon: Double): DataResource<Weather>
}