package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.domain.model.Weather

interface WeatherLocalDataSource {

    suspend fun saveWeather(weather: Weather)
//    suspend fun fetchBirthday(userId: String): String?
//
//    suspend fun saveBirthday(userId: String, birthDate: String)
}
