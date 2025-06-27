package com.todaylab.cleanarchtemplate.local.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.LuckyResultLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.LuckyResultEntity
import com.todaylab.cleanarchtemplate.local.room.dao.LuckyResultDao
import com.todaylab.cleanarchtemplate.local.toData
import com.todaylab.cleanarchtemplate.local.toLocal
import javax.inject.Inject


class LuckyResultLocalDataSourceImpl @Inject constructor(
    private val luckyDao: LuckyResultDao
): LuckyResultLocalDataSource {
    override suspend fun getLuckyResult(id: String): DataResource<LuckyResultEntity> {
        val luckyResult = luckyDao.getLuckyResult(id) ?: return DataResource.empty()
        return DataResource.success(luckyResult.toData())
    }

    override suspend fun saveLuckyResult(luckyResult: LuckyResultEntity) {
        luckyDao.saveLuckyResult(luckyResult.toLocal())
    }
}
