package com.todaylab.photodiary.data.impl

import com.todaylab.photodiary.data.local.DiaryLocalDataSource
import com.todaylab.photodiary.data.mapper.toData
import com.todaylab.photodiary.data.toDomain
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import timber.log.Timber
import javax.inject.Inject

class DiaryRepositoryImpl @Inject constructor(
    private val diaryLocalDataSource: DiaryLocalDataSource
) : DiaryRepository {

    override suspend fun getAll(): List<Diary> {
        return diaryLocalDataSource.getAll().map { it.toDomain() }
    }

    override suspend fun getById(id: Long): Diary? {
        return diaryLocalDataSource.getById(id)?.toDomain()
    }

    override suspend fun save(item: Diary): Boolean {
        return try {
            diaryLocalDataSource.save(item.toData())
            true
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            false
        }
    }

    override suspend fun delete(id: Long): Boolean {
        return try {
            diaryLocalDataSource.delete(id) // id 직접 전달
            true
        } catch (e: Exception) {
            Timber.e("data layer error - ${e.message}")
            false
        }
    }
}
