package com.todaylab.cleanarchtemplate.local.datastore

import androidx.datastore.preferences.core.stringPreferencesKey

object BirthDateKeys {
    val YEAR = stringPreferencesKey("birth_year")
    val MONTH = stringPreferencesKey("birth_month")
    val DAY = stringPreferencesKey("birth_day")
}
