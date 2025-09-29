package com.todaylab.photodiary.domain.repository

import com.todaylab.photodiary.domain.model.Diary

interface DiaryRepository {
    suspend fun getAll(): List<Diary>
    suspend fun getById(id: Long): Diary?
    suspend fun save(item: Diary): Boolean
    suspend fun delete(id: Long): Boolean
}
