package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult

/**
 * domain luckyResult repository
 *
 */
interface LuckyResultRepository {
    suspend fun getLuckyResult(id: String): DataResource<LuckyResult>
}