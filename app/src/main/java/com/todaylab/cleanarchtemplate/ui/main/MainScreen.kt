package com.todaylab.cleanarchtemplate.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.todaylab.cleanarchtemplate.presentation.navigation.AppRoute
import com.todaylab.cleanarchtemplate.presentation.weather.WeatherViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    viewModel: WeatherViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val weatherUiState by viewModel.weatherUiState.collectAsState()

    var isPermissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(), // 1. 위치 권한 요청
        onResult = { isGranted ->
            Log.e("isGranted 권한", isGranted.toString())
            if (isGranted) {
                isPermissionGranted = isGranted
                viewModel.fetchWeatherWithCurrentLocation(context)
            } else {
                // 권한 거부됨 → 에러 처리 또는 기본 위치 사용 등
            }
        }
    )

    LaunchedEffect(Unit) {
        Log.e("처음 권한", isPermissionGranted.toString())
        isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED


        if (!isPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) // 권한이 없으면 팝업 띄움
        } else {
            viewModel.fetchWeatherWithCurrentLocation(context) // 권한이 있으면 날씨 요청
        }
        Log.e("나중 권한", isPermissionGranted.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("메인 화면 - 권한 허용 ${if (isPermissionGranted) "O" else "X"}") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when {
                weatherUiState.isLoading -> {
                    Text("Loading...")
                }

                weatherUiState.weather != null -> {
                    Text("Weather: ${weatherUiState.weather?.main}°C")
                }

                weatherUiState.errorMessage != null -> {
                    Text("Error: ${weatherUiState.errorMessage}")
                }
            }
            Button(onClick = { navController.navigate(AppRoute.ScreenA.route) }) {
                Text("Screen A")
            }
            Button(onClick = { navController.navigate(AppRoute.ScreenB.route) }) {
                Text("Screen B")
            }
            Button(onClick = { navController.navigate(AppRoute.Fortune.route) }) {
                Text("Fortune")
            }
        }
    }
}
