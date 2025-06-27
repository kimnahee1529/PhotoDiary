package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.model.LuckyResultEntity

interface LuckyResultLocalDataSource {
    suspend fun getLuckyResult(id: String): DataResource<LuckyResultEntity>?
    suspend fun saveLuckyResult(luckyResult: LuckyResultEntity)

}