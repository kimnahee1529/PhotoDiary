package com.todaylab.photodiary.domain.usecase

import com.todaylab.photodiary.domain.model.Diary

interface GetDiaryUseCase {
    suspend operator fun invoke(id: Long): Diary?
}