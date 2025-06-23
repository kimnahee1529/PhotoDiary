package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.domain.model.BirthDate

interface BirthDateRepository {
    suspend fun saveBirthDate(birthDate: BirthDate)
    suspend fun getBirthDate(): BirthDate?
}