package com.todaylab.photodiary.domain.usecase

interface GetSolutionByMagicBookUseCase {
    suspend operator fun invoke(): String
}