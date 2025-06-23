package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
import javax.inject.Inject

class SaveBirthDateUseCaseImpl @Inject constructor(
    private val birthDateRepository: BirthDateRepository,
): SaveBirthDateUseCase {
    override suspend operator fun invoke(year: String, month: String, day: String) {
        return birthDateRepository.saveBirthDate(year, month, day)
    }

}