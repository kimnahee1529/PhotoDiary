package com.todaylab.cleanarchtemplate.domain.usecase

interface SaveBirthDateUseCase{
    suspend operator fun invoke(year: String, month: String, day: String)
}