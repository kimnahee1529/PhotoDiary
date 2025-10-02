package com.todaylab.photodiary.domain.impl

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.LuckyResult
import com.todaylab.photodiary.domain.repository.LuckyResultRepository
import javax.inject.Inject

class GetLuckyResultByIdUseCase @Inject constructor(
    private val luckyResultRepository: LuckyResultRepository,
) {
    suspend fun invoke(id: String): DataResource<LuckyResult> {
        return luckyResultRepository.getById(id)
    }
}
