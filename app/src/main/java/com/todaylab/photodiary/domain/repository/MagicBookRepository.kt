package com.todaylab.photodiary.domain.repository

import BaseSingletonRepository
import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.domain.model.Solution
import kotlinx.coroutines.flow.Flow

/**
 * domain magicBook repository
 */
interface MagicBookRepository : BaseSingletonRepository<String> {
    suspend fun getSolution(): Flow<DataResource<Solution>>
}