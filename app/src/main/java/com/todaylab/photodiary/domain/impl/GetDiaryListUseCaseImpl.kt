package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import com.todaylab.photodiary.domain.usecase.GetDiaryListUseCase
import javax.inject.Inject

class GetDiaryListUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository,
) : GetDiaryListUseCase {
    override suspend fun invoke(): List<Diary> {
        return diaryRepository.getAll()
    }
}