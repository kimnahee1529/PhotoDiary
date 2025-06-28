package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult
import com.todaylab.cleanarchtemplate.domain.repository.LuckyResultRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultByIdUseCase
import javax.inject.Inject

class GetLuckyResultByIdUseCaseImpl @Inject constructor(
    private val luckyResultRepository: LuckyResultRepository,
) : GetLuckyResultByIdUseCase {

    override suspend fun invoke(id: String): DataResource<LuckyResult> {
        return luckyResultRepository.getById(id)
    }
}
