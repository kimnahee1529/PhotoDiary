package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.model.BirthDate

interface GetBirthDateUseCase{
    suspend operator fun invoke(): BirthDate?
}