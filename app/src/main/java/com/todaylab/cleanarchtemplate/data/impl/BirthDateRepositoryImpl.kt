package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.data.local.BirthDateLocalDataSource
import com.todaylab.cleanarchtemplate.data.local.WeatherLocalDataSource
import com.todaylab.cleanarchtemplate.data.remote.WeatherRemoteDataSource
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.Weather
import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import javax.inject.Inject

class BirthDateRepositoryImpl @Inject constructor(
    private val birthDateLocalDataSource: BirthDateLocalDataSource
): BirthDateRepository{
    override suspend fun saveBirthDate(year: String, month: String, day: String) {
        return birthDateLocalDataSource.saveBirthDate(year, month, day)
    }

    override suspend fun getBirthDate(): String? {
        return birthDateLocalDataSource.getBirthDate()
    }

}

