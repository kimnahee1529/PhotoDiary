package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.domain.model.BirthDate

interface SaveBirthDateUseCase{
    suspend operator fun invoke(birthDate: BirthDate)
}