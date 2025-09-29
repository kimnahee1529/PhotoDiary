package com.todaylab.photodiary.local.impl

import android.content.Context
import com.todaylab.photodiary.data.local.DiaryLocalDataSource
import com.todaylab.photodiary.data.model.DiaryEntity
import com.todaylab.photodiary.local.mapper.toData
import com.todaylab.photodiary.local.mapper.toLocal
import com.todaylab.photodiary.local.room.dao.DiaryDao
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class DiaryLocalDataSourceImpl @Inject constructor(
    private val diaryDao: DiaryDao,
    @ApplicationContext private val context: Context
) : DiaryLocalDataSource {

    override suspend fun getAll(): List<DiaryEntity> {
        return diaryDao.getAll().map { it.toData() }
    }

    override suspend fun getById(id: Long): DiaryEntity? {
        return diaryDao.getById(id)?.toData()
    }

    override suspend fun save(item: DiaryEntity): Boolean {
        try {
            diaryDao.insert(item.toLocal())
            return true
        } catch (e: Exception) {
            return false
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            diaryDao.deleteById(id) // DAO에 이 메서드 있어야 함
            true
        } catch (e: Exception) {
            false
        }
    }

}