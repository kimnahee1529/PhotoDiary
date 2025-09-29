package com.todaylab.photodiary.data.local

import com.todaylab.photodiary.data.BaseSingletonDataSource
import com.todaylab.photodiary.data.model.DiaryEntity

interface DiaryLocalDataSource {
    suspend fun getAll(): List<DiaryEntity>
    suspend fun getById(id: Long): DiaryEntity?
    suspend fun save(item: DiaryEntity): Boolean
    suspend fun delete(id: Long): Boolean
}