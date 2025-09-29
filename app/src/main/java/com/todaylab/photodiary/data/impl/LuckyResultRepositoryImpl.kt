package com.todaylab.photodiary.data.impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.local.LuckyResultLocalDataSource
import com.todaylab.photodiary.data.mapper.LuckyResultMapper
import com.todaylab.photodiary.domain.model.LuckyResult
import com.todaylab.photodiary.domain.repository.LuckyResultRepository
import timber.log.Timber
import javax.inject.Inject

class LuckyResultRepositoryImpl @Inject constructor(
    private val localDataSource: LuckyResultLocalDataSource,
) : LuckyResultRepository {

    /**
     * Get lucky result by id(yyyyMMdd) from local
     * If not found, generate new result and save to local
     */
    override suspend fun getById(id: String): DataResource<LuckyResult> {
        val savedLuckyResult = localDataSource.getById(id)
        Timber.d("savedLuckyResult: ${savedLuckyResult}")

        return if (savedLuckyResult.getDataOrNull() != null) {
            savedLuckyResult.mapData(LuckyResultMapper::mapToHigh)
        } else {
            val newLuckyResult = LuckyResult.generateLuckyResult()
            Timber.d("newLuckyResult: ${newLuckyResult}")
            localDataSource.save(LuckyResultMapper.mapToLow(newLuckyResult))
            DataResource.Success(newLuckyResult)
        }
    }

    /**
     * Get all lucky results from local
     * If not found, return empty list
     */
    override suspend fun getAll(): DataResource<List<LuckyResult>> {
        val savedLuckyResults = localDataSource.getAll()
        Timber.d("savedLuckyResults: ${savedLuckyResults}")

        return if (savedLuckyResults.getDataOrNull() != null) {
            savedLuckyResults.mapData { it.map(LuckyResultMapper::mapToHigh) }
        } else {
            DataResource.Success(emptyList())
        }
    }

    override suspend fun save(item: LuckyResult): Boolean {
        return localDataSource.save(LuckyResultMapper.mapToLow(item))
    }

    override suspend fun delete(item: LuckyResult): Boolean {
        return localDataSource.delete(LuckyResultMapper.mapToLow(item))
    }
}
