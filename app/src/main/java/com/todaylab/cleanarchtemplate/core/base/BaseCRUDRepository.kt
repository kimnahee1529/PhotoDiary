package com.todaylab.cleanarchtemplate.core.base

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * Repository interface for CRUD operations on a generic type [T].
 */
interface BaseCRUDRepository<T> {
    suspend fun getById(id: String): DataResource<T>
    suspend fun getAll(): DataResource<List<T>>
    suspend fun save(item: T): Boolean
    suspend fun delete(item: T): Boolean
}