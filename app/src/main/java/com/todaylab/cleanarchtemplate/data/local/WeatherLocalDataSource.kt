package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.BaseSingletonDataSource
import com.todaylab.cleanarchtemplate.data.model.WeatherEntity

interface WeatherLocalDataSource : BaseSingletonDataSource<WeatherEntity> {
    suspend fun getByLocation(lat: Double, lon: Double): DataResource<WeatherEntity>
}