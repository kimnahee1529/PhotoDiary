package com.todaylab.photodiary.domain.usecase

import com.todaylab.photodiary.domain.model.Diary

interface SaveDiaryUseCase {
    suspend operator fun invoke(diary: Diary)
}