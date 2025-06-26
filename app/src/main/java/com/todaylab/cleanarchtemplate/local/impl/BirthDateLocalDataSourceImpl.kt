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
import javax.inject.Inject


class BirthDateLocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
): BirthDateLocalDataSource {
    override suspend fun saveBirthDate(birthDate: BirthDateEntity) {
        context.birthDateDataStore.edit { prefs ->
            prefs[BirthDateKeys.YEAR] = birthDate.year
            prefs[BirthDateKeys.MONTH] = birthDate.month
            prefs[BirthDateKeys.DAY] = birthDate.day
        }
    }

    override suspend fun getBirthDate(): DataResource<BirthDateEntity>? {
        try {
            val prefs = context.birthDateDataStore.data.first()
            val y = prefs[BirthDateKeys.YEAR]
            val m = prefs[BirthDateKeys.MONTH]
            val d = prefs[BirthDateKeys.DAY]
            if (y == null || m == null || d == null) return null
            return DataResource.success(BirthDateEntity(y, m, d))
        } catch (e: Exception) {
            return DataResource.error(Throwable("local layer error - ${e.message}"))
        }
    }

}
