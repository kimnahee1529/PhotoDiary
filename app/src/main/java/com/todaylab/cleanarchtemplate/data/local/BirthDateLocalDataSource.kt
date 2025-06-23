package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.data.model.BirthDateEntity

interface BirthDateLocalDataSource {
    suspend fun saveBirthDate(birthDateEntity: BirthDateEntity)
    suspend fun getBirthDate(): BirthDateEntity?
}
