package com.todaylab.photodiary.domain.usecase

import com.todaylab.photodiary.domain.model.Diary

interface GetDiaryListUseCase {
    suspend operator fun invoke(): List<Diary>
}