package com.todaylab.cleanarchtemplate.domain.usecase

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult

interface GetLuckyResultByIdUseCase {
    suspend operator fun invoke(id: String): DataResource<LuckyResult>
}