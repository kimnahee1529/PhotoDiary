package com.todaylab.cleanarchtemplate.domain.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult
import com.todaylab.cleanarchtemplate.domain.repository.LuckyResultRepository
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultUseCase
import javax.inject.Inject

class GetLuckyResultUseCaseImpl @Inject constructor(
    private val luckyResultRepository: LuckyResultRepository,
) : GetLuckyResultUseCase {

    override suspend fun invoke(id: String): DataResource<LuckyResult> {
        return luckyResultRepository.getById(id)
    }
}
