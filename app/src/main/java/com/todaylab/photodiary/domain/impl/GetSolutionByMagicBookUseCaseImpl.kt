package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.repository.MagicBookRepository
import com.todaylab.photodiary.domain.usecase.GetSolutionByMagicBookUseCase
import javax.inject.Inject

class GetSolutionByMagicBookUseCaseImpl @Inject constructor(
    private val magicBookRepository: MagicBookRepository
) : GetSolutionByMagicBookUseCase {
    override suspend operator fun invoke(): String {
        return magicBookRepository.getSolution()
    }
}