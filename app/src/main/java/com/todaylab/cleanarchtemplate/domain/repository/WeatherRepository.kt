package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.Weather

/**
 * domain weather repository
 * - 좌표에 따른 오늘 날씨 반환
 */
interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): DataResource<Weather>
}