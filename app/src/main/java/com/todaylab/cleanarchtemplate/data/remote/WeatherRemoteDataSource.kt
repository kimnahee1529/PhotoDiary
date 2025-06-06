package com.todaylab.cleanarchtemplate.data.remote

import com.todaylab.cleanarchtemplate.data.model.WeatherEntity


/**
 * data layer
 * weather remote data source interface
 */
interface WeatherRemoteDataSource {
    fun getCurrentWeather(): WeatherEntity
}
