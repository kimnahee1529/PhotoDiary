package com.todaylab.cleanarchtemplate.ui.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import coil3.compose.AsyncImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.ui.model.WeatherState
import timber.log.Timber

/**
 * Current weather icon composable function
 * @param weather weather state data resource
 * @param shouldRequestLocationPermission request location permission if true
 * @param saveLocation call back to save location after permission granted
 * @param modifier Composable Modifier
 */
@Composable
fun CurrentWeatherIcon(
    weather: DataResource<WeatherState> = DataResource.loading(),
    shouldRequestLocationPermission: Boolean = true,
    saveLocation: (lat: Double, lon: Double) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    /**
     * Location permission launcher
     * ask for location permission & fetch location if granted
     */
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("location permission granted on launcher")
            fetchLocation(fusedLocationClient) { lat, lon ->
                Timber.d("fetch location - $lat, $lon")
                if (lat == null || lon == null) return@fetchLocation
                saveLocation(lat, lon)
            }
        } else {
            Timber.d("location permission denied on launcher")
        }
    }

    /*
     * Check if location permission is granted
     * request permission if `shouldRequestLocationPermission` is true
     */
    LaunchedEffect(Unit) {
        when (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION
        )) {
            PermissionChecker.PERMISSION_GRANTED -> {
                Timber.d("granted location permission on launch")
                fetchLocation(fusedLocationClient) { lat, lon ->
                    Timber.d("fetch location - $lat, $lon")
                    if (lat == null || lon == null) return@fetchLocation
                    saveLocation(lat, lon)
                }
            }

            else -> {
                Timber.d("no location permission on launch")
                if (!shouldRequestLocationPermission) return@LaunchedEffect
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    WeatherIcon(weather, modifier)
}

/**
 * Weather icon composable function
 * render open weather icon image
 */
@Composable
private fun WeatherIcon(
    weather: DataResource<WeatherState>,
    modifier: Modifier = Modifier,
) {
    when (weather) {
        is DataResource.Loading -> if (weather.data != null) AsyncImage(
            model = "https://openweathermap.org/img/wn/${weather.data?.icon}@2x.png",
            contentDescription = weather.data.main,
            modifier = modifier,
        ) else Text("Loading...")
        is DataResource.Empty -> Text("Empty", modifier = modifier)
        is DataResource.Success -> AsyncImage(
            model = "https://openweathermap.org/img/wn/${weather.data.icon}@2x.png",
            contentDescription = weather.data.main,
            modifier = modifier,
        )
        is DataResource.Error -> Text(
            text = "Error: ${weather.throwable.message}", modifier = modifier
        )
    }
}

/**
 * Fetch current location
 * @param fusedLocationClient FusedLocationProviderClient
 * @param onLocationFetched last location fetched callback
 */
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