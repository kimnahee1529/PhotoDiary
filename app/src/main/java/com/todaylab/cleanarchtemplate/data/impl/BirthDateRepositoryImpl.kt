package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.BirthDateLocalDataSource
import com.todaylab.cleanarchtemplate.data.mapper.BirthDateMapper
import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import timber.log.Timber
import javax.inject.Inject

class BirthDateRepositoryImpl @Inject constructor(
    private val birthDateLocalDataSource: BirthDateLocalDataSource
): BirthDateRepository{
    override suspend fun get(): DataResource<BirthDate> {
        try {
            val localBirthDate = birthDateLocalDataSource.get()
            return localBirthDate.mapData(BirthDateMapper::mapToHigh)
        } catch (e: Exception) {
            return DataResource.error(Throwable("data layer error - ${e.message}"))
        }
    }

    override suspend fun save(item: BirthDate): Boolean {
        try {
            birthDateLocalDataSource.save(BirthDateMapper.mapToLow(item))
            return true
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            return false
        }
    }

    override suspend fun delete(): Boolean {
        try {
            birthDateLocalDataSource.delete()
            return true
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            return false
        }
    }
}

