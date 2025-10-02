package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.domain.repository.MagicBookRepository
import javax.inject.Inject

class GetSolutionByMagicBookUseCase @Inject constructor(
    private val magicBookRepository: MagicBookRepository
) {
    suspend operator fun invoke() =
        magicBookRepository.getSolution()
}