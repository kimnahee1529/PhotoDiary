package com.todaylab.photodiary.domain.usecase

import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.LuckyResult

interface GetLuckyResultByIdUseCase {
    suspend operator fun invoke(id: String): DataResource<LuckyResult>
}