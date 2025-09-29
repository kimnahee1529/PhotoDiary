package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import com.todaylab.photodiary.domain.usecase.GetDiaryUseCase
import javax.inject.Inject

class GetDiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : GetDiaryUseCase {
    override suspend fun invoke(id: Long): Diary? {
        return diaryRepository.getById(id)
    }
}