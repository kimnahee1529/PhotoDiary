package com.todaylab.cleanarchtemplate.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.birthDateDataStore: DataStore<Preferences> by preferencesDataStore(name = "birth_date_prefs")
