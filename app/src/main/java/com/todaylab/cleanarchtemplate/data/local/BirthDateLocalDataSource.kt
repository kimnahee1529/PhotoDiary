package com.todaylab.cleanarchtemplate.data.local

import com.todaylab.cleanarchtemplate.domain.model.Weather

interface BirthDateLocalDataSource {

    suspend fun saveBirthDate(year: String, month: String, day: String)
    suspend fun getBirthDate(): String?
}
