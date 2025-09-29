package com.todaylab.photodiary.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.locationDataStore: DataStore<Preferences> by preferencesDataStore(name = "location_prefs")
val Context.magicBookDataStore: DataStore<Preferences> by preferencesDataStore(name = "magicBook_prefs")
val Context.plantDataStore: DataStore<Preferences> by preferencesDataStore(name = "plant_prefs")
