package com.todaylab.cleanarchtemplate.local.impl

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.data.local.BirthDateLocalDataSource
import com.todaylab.cleanarchtemplate.data.model.BirthDateEntity
import com.todaylab.cleanarchtemplate.local.datastore.BirthDateKeys
import com.todaylab.cleanarchtemplate.local.datastore.birthDateDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject


class BirthDateLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : BirthDateLocalDataSource {
    override suspend fun get(): DataResource<BirthDateEntity> {
        try {
            val prefs = context.birthDateDataStore.data.first()
            val y = prefs[BirthDateKeys.YEAR]
            val m = prefs[BirthDateKeys.MONTH]
            val d = prefs[BirthDateKeys.DAY]
            if (y == null || m == null || d == null) return DataResource.empty()
            return DataResource.success(BirthDateEntity(y, m, d))
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

    override suspend fun save(item: BirthDateEntity): Boolean {
        try {
            context.birthDateDataStore.edit { prefs ->
                prefs[BirthDateKeys.YEAR] = item.year
                prefs[BirthDateKeys.MONTH] = item.month
                prefs[BirthDateKeys.DAY] = item.day
            }
            return true
        } catch (e: Exception) {
            Timber.e("local layer error - ${e.message}")
            return false
        }
    }

    override suspend fun delete(): Boolean {
        try {
            context.birthDateDataStore.edit {
                it.clear()
            }
            return true
        } catch (e: Exception) {
            Timber.e("local layer error - ${e.message}")
            return false
        }
    }
}
