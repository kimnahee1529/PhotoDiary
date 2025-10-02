package com.todaylab.photodiary.local.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.local.LuckyResultLocalDataSource
import com.todaylab.photodiary.data.model.LuckyResultEntity
import com.todaylab.photodiary.local.mapper.LuckyResultMapper
import com.todaylab.photodiary.local.room.dao.LuckyResultDao
import javax.inject.Inject


class LuckyResultLocalDataSourceImpl @Inject constructor(
    private val luckyDao: LuckyResultDao
) : LuckyResultLocalDataSource {
    override suspend fun getById(id: String): DataResource<LuckyResultEntity> {
        try {
            val luckyResult = luckyDao.getById(id) ?: return DataResource.loading()
            return DataResource.success(LuckyResultMapper.mapToHigh(luckyResult))
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun getAll(): DataResource<List<LuckyResultEntity>> {
        try {
            val luckyResult = luckyDao.getAll()
            return DataResource.success(
                luckyResult.map { LuckyResultMapper.mapToHigh(it) }
            )
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun save(item: LuckyResultEntity): Boolean {
        try {
            luckyDao.save(LuckyResultMapper.mapToLow(item))
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun delete(item: LuckyResultEntity): Boolean {
        try {
            luckyDao.deleteById(item.id)
            return true
        } catch (e: Exception) {
            return false
        }
    }
}
