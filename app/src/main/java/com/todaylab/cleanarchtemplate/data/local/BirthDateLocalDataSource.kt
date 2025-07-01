package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.model.BirthDateEntity

interface BirthDateLocalDataSource {
    suspend fun getBirthDate(): DataResource<BirthDateEntity>
    suspend fun saveBirthDate(birthDateEntity: BirthDateEntity)
}
