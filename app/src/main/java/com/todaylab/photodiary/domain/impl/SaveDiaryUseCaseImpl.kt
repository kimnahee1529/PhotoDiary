package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.model.BirthDate
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import com.todaylab.photodiary.domain.usecase.SaveDiaryUseCase
import javax.inject.Inject

class SaveDiaryUseCaseImpl @Inject constructor(
    private val diaryRepository: DiaryRepository,
): SaveDiaryUseCase {
    override suspend fun invoke(diary: Diary) {
        diaryRepository.save(diary)
    }
}