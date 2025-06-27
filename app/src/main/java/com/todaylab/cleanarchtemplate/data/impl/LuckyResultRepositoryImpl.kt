package com.todaylab.cleanarchtemplate.data.impl

import android.os.Build
import androidx.annotation.RequiresApi
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.LuckyResultLocalDataSource
import com.todaylab.cleanarchtemplate.data.toData
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult
import com.todaylab.cleanarchtemplate.domain.repository.LuckyResultRepository
import timber.log.Timber
import javax.inject.Inject

class LuckyResultRepositoryImpl @Inject constructor(
    private val luckyResultLocalDataSource: LuckyResultLocalDataSource,
) : LuckyResultRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getLuckyResult(id: String): DataResource<LuckyResult> {
        val savedLuckyResult = luckyResultLocalDataSource.getLuckyResult(id)

        return if (savedLuckyResult.getDataOrNull() != null) {
            Timber.d("savedLuckyResult: ${savedLuckyResult}")
            savedLuckyResult.mapData { it.toDomain() }
        } else {
            val newLuckyResult = LuckyResult.generateLuckyResult()
            Timber.d("newLuckyResult: ${newLuckyResult}")
            luckyResultLocalDataSource.saveLuckyResult(newLuckyResult.toData())
            DataResource.Success(newLuckyResult)
        }
    }
}
