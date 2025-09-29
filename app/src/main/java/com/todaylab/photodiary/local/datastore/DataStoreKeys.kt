package com.todaylab.photodiary.local.datastore

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey

internal object PermissionKeys {
    val LOCATION_GRANTED = booleanPreferencesKey("location_granted")
}

object MagicBookKeys {
    val LAST_SHOWN_DATE = stringPreferencesKey("last_shown_date")
    val LAST_SOLUTION = stringPreferencesKey("last_solution")
}

object WateringPrefs {
    val WATERING_DATES = stringSetPreferencesKey("watering_dates")
}
