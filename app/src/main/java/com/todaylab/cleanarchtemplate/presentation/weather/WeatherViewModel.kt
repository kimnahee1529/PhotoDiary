package com.todaylab.cleanarchtemplate.presentation.weather

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
) : ViewModel() {
    private val _weatherUiState = MutableStateFlow(WeatherUiState())
    val weatherUiState: StateFlow<WeatherUiState> = _weatherUiState.asStateFlow()

    init {
        loadWeather(lat = 37.5, lon = 127.0)
    }

    private fun loadWeather(lat: Double, lon: Double) {
        viewModelScope.launch {
            _weatherUiState.update { it.copy(isLoading = true) }

            try {
                val weather = getWeatherUseCase(lat, lon)
                saveWeatherUseCase(weather)
                _weatherUiState.update {
                    it.copy(
                        isLoading = false,
                        weather = weather,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _weatherUiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }

    fun fetchWeatherWithCurrentLocation(context: Context) {
        viewModelScope.launch {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            val hasPermission = ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                // 권한이 없으면 요청하거나 예외 처리
                return@launch
            }

            try {
                val location = fusedLocationClient.lastLocation.await()
                if (location != null) {
                    val lat = location.latitude
                    val lon = location.longitude
                    loadWeather(lat, lon)
                }
            } catch (e: SecurityException) {
                // 위치 권한 거부됨
            } catch (e: Exception) {
                // 다른 예외
            }
        }
    }

}