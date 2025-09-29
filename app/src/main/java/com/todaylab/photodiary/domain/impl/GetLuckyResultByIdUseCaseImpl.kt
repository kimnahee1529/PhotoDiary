package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.LuckyResult
import com.todaylab.photodiary.domain.repository.LuckyResultRepository
import com.todaylab.photodiary.domain.usecase.GetLuckyResultByIdUseCase
import javax.inject.Inject

class GetLuckyResultByIdUseCaseImpl @Inject constructor(
    private val luckyResultRepository: LuckyResultRepository,
) : GetLuckyResultByIdUseCase {

    override suspend fun invoke(id: String): DataResource<LuckyResult> {
        return luckyResultRepository.getById(id)
    }
}
