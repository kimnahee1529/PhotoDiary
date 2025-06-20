package com.todaylab.cleanarchtemplate.domain.usecase

interface GetBirthDateUseCase{
    suspend operator fun invoke(): String?
}