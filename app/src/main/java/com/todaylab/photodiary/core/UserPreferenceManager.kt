package com.todaylab.photodiary.core

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.todaylab.photodiary.local.datastore.MagicBookKeys
import com.todaylab.photodiary.local.datastore.PermissionKeys
import com.todaylab.photodiary.local.datastore.WateringPrefs.WATERING_DATES
import com.todaylab.photodiary.local.datastore.locationDataStore
import com.todaylab.photodiary.local.datastore.magicBookDataStore
import com.todaylab.photodiary.local.datastore.plantDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

class UserPreferenceManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val locationDataStore = context.locationDataStore
    private val solutionDataStore = context.magicBookDataStore
    private val plantDataStore = context.plantDataStore

    suspend fun saveLocationPermissionGranted(granted: Boolean) {
        locationDataStore.edit { prefs ->
            prefs[PermissionKeys.LOCATION_GRANTED] = granted
        }
    }

    fun readLocationPermissionGranted(): Flow<Boolean> =
        locationDataStore.data.map { prefs ->
            prefs[PermissionKeys.LOCATION_GRANTED] ?: false
        }

    fun readLastSolution(): Flow<Pair<String?, String?>> =
        solutionDataStore.data.map { prefs ->
            val date = prefs[MagicBookKeys.LAST_SHOWN_DATE]
            val solution = prefs[MagicBookKeys.LAST_SOLUTION]
            Timber.d("해답 date: $date, solution: $solution")
            date to solution
        }

    suspend fun saveTodaySolution(date: String, solution: String) {
        solutionDataStore.edit { prefs ->
            prefs[MagicBookKeys.LAST_SHOWN_DATE] = date
            prefs[MagicBookKeys.LAST_SOLUTION] = solution
        }
    }

    suspend fun addWateringDate() {
        val today = LocalDate.now().toString()
        plantDataStore.edit { prefs ->
            val current = prefs[WATERING_DATES] ?: emptySet()
            prefs[WATERING_DATES] = current + today
        }
    }

    fun getWateringDates(): Flow<Set<String>> {
        return plantDataStore.data.map { prefs ->
            Timber.d("날짜: $prefs")
            prefs[WATERING_DATES] ?: emptySet()
        }
    }

    suspend fun addWateringDates() {
        val today = LocalDate.now()
        val threeDays = setOf(
            today.toString(),                      // 오늘
            today.minusDays(1).toString(),         // 어제
            today.minusDays(2).toString()          // 그제
        )

        plantDataStore.edit { prefs ->
            prefs[WATERING_DATES] = threeDays
        }
    }

}
