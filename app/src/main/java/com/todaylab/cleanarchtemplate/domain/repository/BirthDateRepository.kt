package com.todaylab.cleanarchtemplate.domain.repository

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.model.BirthDate

interface BirthDateRepository {
    suspend fun getBirthDate(): DataResource<BirthDate>
    suspend fun saveBirthDate(birthDate: BirthDate)
}
