package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.local.model.BirthdayLocal
import com.todaylab.cleanarchtemplate.local.room.dao.WeatherDao
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(
    private val weatherDao: WeatherDao
): WeatherLocalDataSource {

    override suspend fun saveWeather(weather: Weather) {

    }
}
