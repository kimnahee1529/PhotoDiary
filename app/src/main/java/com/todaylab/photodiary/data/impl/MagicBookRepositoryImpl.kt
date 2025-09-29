package com.todaylab.photodiary.data.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.data.local.MagicBookLocalDataSource
import com.todaylab.photodiary.data.mapper.DiaryMapper
import com.todaylab.photodiary.data.mapper.LuckyResultMapper
import com.todaylab.photodiary.data.mapper.LuckyResultMapper.mapToHigh
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.model.LuckyResult
import com.todaylab.photodiary.domain.repository.DiaryRepository
import com.todaylab.photodiary.domain.repository.MagicBookRepository
import timber.log.Timber
import javax.inject.Inject

class MagicBookRepositoryImpl @Inject constructor(
    private val magicBookLocalDataSource: MagicBookLocalDataSource
) : MagicBookRepository {

    override suspend fun getSolution(): String {
        val savedSolution = magicBookLocalDataSource.loadSolutions()
        val randomSolution = savedSolution.random() // 리스트에서 무작위로 하나 선택
        Timber.d("saved solution: $randomSolution")
        return randomSolution.text
    }

    override suspend fun get(): DataResource<String> {
        TODO("Not yet implemented")
    }

    override suspend fun save(item: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(): Boolean {
        TODO("Not yet implemented")
    }

}

