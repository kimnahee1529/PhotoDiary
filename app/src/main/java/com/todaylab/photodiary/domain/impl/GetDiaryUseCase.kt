package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import javax.inject.Inject

class GetDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
) {
    suspend fun invoke(id: Long): Diary? {
        return diaryRepository.getById(id)
    }
}