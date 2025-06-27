package com.todaylab.cleanarchtemplate.core.base

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * Data source interface for CRUD operations on a generic type [TEntity].
 */
interface BaseCRUDDataSource<TEntity> {
    suspend fun getById(id: String): DataResource<TEntity>
    suspend fun getAll(): DataResource<List<TEntity>>
    suspend fun save(item: TEntity): Boolean
    suspend fun delete(item: TEntity): Boolean
}