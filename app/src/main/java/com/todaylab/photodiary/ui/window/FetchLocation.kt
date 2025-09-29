package com.todaylab.photodiary.ui.window

import android.annotation.SuppressLint
import com.google.android.gms.location.FusedLocationProviderClient
import timber.log.Timber

@SuppressLint("MissingPermission")
private fun fetchLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFetched: (lat: Double?, lon: Double?) -> Unit
) {
    val locationTask = fusedLocationClient.lastLocation
    locationTask.addOnSuccessListener { location ->
        Timber.d("fetch location success - ${location?.latitude}, ${location?.longitude}")
        onLocationFetched(location?.latitude, location?.longitude)
    }
    locationTask.addOnFailureListener { exception ->
        Timber.e("fetch location failed - $exception")
        onLocationFetched(null, null)
    }
}