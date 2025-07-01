package com.todaylab.cleanarchtemplate.data

import com.todaylab.cleanarchtemplate.core.DataResource

/**
 * Data source interface for singleton data on a generic type [TEntity].
 */
interface BaseSingletonDataSource<TEntity> {
    suspend fun get(): DataResource<TEntity>
    suspend fun save(item: TEntity): Boolean
    suspend fun delete(): Boolean
}