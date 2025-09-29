package com.todaylab.photodiary.ui.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import coil3.compose.AsyncImage
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.todaylab.photodiary.core.DataResource
import com.todaylab.photodiary.ui.model.WeatherState
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

    Box(modifier = Modifier.fillMaxWidth()) {
        when (weather) {
            is DataResource.Loading -> {
                if (weather.data != null) WeatherIcon(
                    weather.data,
                    Modifier.align(Alignment.CenterEnd)
                )
                else Text(
                    "로딩중",
                    Modifier.align(Alignment.CenterEnd)
                )
            }

            is DataResource.Success -> WeatherIcon(
                weather.data,
                Modifier.align(Alignment.CenterEnd)
            )

            else -> return
        }
    }
}

/**
 * Weather icon composable function
 * render open weather icon image
 */
@Composable
private fun WeatherIcon(
    weatherState: WeatherState,
    modifier: Modifier = Modifier,
) {
//    Row(
//        modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(2.dp)
//    ) {
//        Text(weatherState.iconLabel, style = MaterialTheme.typography.titleMedium)
//        AsyncImage(
//            model = "https://openweathermap.org/img/wn/${weatherState.icon}@2x.png",
//            contentDescription = weatherState.main,
//            modifier = Modifier.size(40.dp)
//        )
//    }
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

//@Preview
//@Composable
//private fun PreviewWeatherIcon() {
//    WeatherIcon(
//        weatherState =
//            WeatherState(
//                date = java.util.Date(),
//                lat = 0.0,
//                lon = 0.0,
//                main = "clear",
//                description = "clear sky",
//                iconLabel = "맑음",
//                icon = "02d",
//            )
//    )
//}