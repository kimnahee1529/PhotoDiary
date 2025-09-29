package com.todaylab.photodiary.data.local

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.BaseSingletonDataSource
import com.todaylab.photodiary.data.model.WeatherEntity

interface WeatherLocalDataSource : BaseSingletonDataSource<WeatherEntity> {
    suspend fun getByLocation(lat: Double, lon: Double): DataResource<WeatherEntity>
}