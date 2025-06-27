package com.todaylab.cleanarchtemplate.data.impl

import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.BirthDateLocalDataSource
import com.todaylab.cleanarchtemplate.data.toData
import com.todaylab.cleanarchtemplate.data.toDomain
import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.domain.repository.BirthDateRepository
import javax.inject.Inject

class BirthDateRepositoryImpl @Inject constructor(
    private val birthDateLocalDataSource: BirthDateLocalDataSource
): BirthDateRepository{
    override suspend fun saveBirthDate(birthDate: BirthDate) {
        birthDateLocalDataSource.saveBirthDate(birthDate.toData())
    }

    override suspend fun getBirthDate(): DataResource<BirthDate> {
        return when (val localBirthDate = birthDateLocalDataSource.getBirthDate()) {
            is DataResource.Error -> localBirthDate
            is DataResource.Empty -> DataResource.empty()
            is DataResource.Loading -> DataResource.loading(localBirthDate.data?.toDomain())
            is DataResource.Success -> DataResource.success(localBirthDate.data.toDomain())
        }
    }

}

