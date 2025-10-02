package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.repository.DiaryRepository
import javax.inject.Inject

class SaveDiaryUseCase @Inject constructor(
    private val diaryRepository: DiaryRepository,
) {
    suspend operator fun invoke(diary: Diary) {
        diaryRepository.save(diary)
    }
}