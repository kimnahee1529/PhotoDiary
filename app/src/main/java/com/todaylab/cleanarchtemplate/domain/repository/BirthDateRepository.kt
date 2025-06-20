package com.todaylab.cleanarchtemplate.domain.repository

interface BirthDateRepository {
    suspend fun saveBirthDate(year: String, month: String, day: String)
    suspend fun getBirthDate(): String?
}