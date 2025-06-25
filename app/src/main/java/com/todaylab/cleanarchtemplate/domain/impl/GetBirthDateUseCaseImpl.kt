package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import javax.inject.Inject

class GetBirthDateUseCaseImpl @Inject constructor(
    private val birthDateRepository: BirthDateRepository,
): GetBirthDateUseCase {
    override suspend operator fun invoke(): BirthDate? {
        try {
            return birthDateRepository.getBirthDate()
        } catch (e: Exception) {
            throw Throwable(e)
        }
    }
}