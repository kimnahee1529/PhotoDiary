package com.todaylab.photodiary.ui.home

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationServices
import com.todaylab.photodiary.R
import com.todaylab.photodiary.presentation.home.HomeViewModel
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.util.noRippleClickable
import com.todaylab.photodiary.ui.util.wobbleClickable
import com.todaylab.photodiary.ui.widget.interactionItem.ToggleWindowImage
import com.todaylab.sketchmind.ui.widget.topappbar.CommonTopAppBar
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate


@Composable
fun HomeRoute(
    navToDiaryList: () -> Unit = {},
    navToMagicBook: () -> Unit = {},
    navToPlant: () -> Unit = {},
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLocationPermissionGranted) {
        viewModel.loadLocationPermissionGranted()
        Timber.d("위치 권한: ${uiState.isLocationPermissionGranted}")
    }

    LaunchedEffect(uiState.weather) {
        Timber.d("날씨: ${uiState.weather.state}")
    }

    HomeScreen(
        uiState = uiState,
        navToDiaryList = navToDiaryList,
        navToMagicBook = navToMagicBook,
        navToPlant = navToPlant,
        onGranted = { lat, lon ->
            viewModel.viewModelScope.launch {
                viewModel.saveLocationPermissionGranted(true)
                viewModel.loadWeather(lat, lon)
            }
        }
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: HomeViewModel.HomeUiState = HomeViewModel.HomeUiState(),
    navToDiaryList: () -> Unit = {},
    navToMagicBook: () -> Unit = {},
    navToPlant: () -> Unit = {},
    onGranted: (Double, Double) -> Unit = { _, _ -> },
    modifier: Modifier = Modifier
) {

    val weather = uiState.weather.state
    var shouldRequestLocationPermission by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val (forceOpenWindow, setForceOpenWindow) = remember { mutableStateOf(false) }

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }
    // 위치 권한 받아오기
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Timber.d("위치 권한 허용눌렀음!!!!!!!")
//            onGranted()
            setForceOpenWindow(true)

            // 좌표 가져오기
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Timber.d("현재 좌표: ${location.latitude}, ${location.longitude}")
                        onGranted(location.latitude, location.longitude)
                        // ViewModel에 저장하거나 DataStore에 저장 가능
                        // viewModel.saveLocation(location.latitude, location.longitude)

                    } else {
                        Timber.d("위치 가져오기 실패 (null)")
                    }
                }
                .addOnFailureListener { exception ->
                    Timber.e("위치 가져오기 실패 - $exception")
                }
            // 좌표 가져오기

            } else {
            Timber.d("위치 권한 허용안함 눌렀음ㅜㅜㅜㅜㅜㅜㅜㅜ")
        }
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED
        ) {
            Timber.d("앱 시작 시 권한 이미 허용됨 → 날씨 요청 바로 실행")
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        Timber.d("앱 시작 좌표: ${location.latitude}, ${location.longitude}")
                        onGranted(location.latitude, location.longitude)
                    }
                }
        }
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colors.white),
        topBar = {
            CommonTopAppBar(
                title = "내 마음 속 공간",
                style = MaterialTheme.typo.titleSmall,
                onActionClick = {}
            )
        },
    ) { innerPadding ->

        // 🔆 불 상태: 시작은 꺼짐(어둡게)
        var isLightOn by remember { mutableStateOf(false) }
        var isClosed by remember { mutableStateOf(true) } // 초기 상태: 창문 닫힘
        val dimAlpha by animateFloatAsState(        // 오버레이 투명도
            targetValue = if (isLightOn) 0f else 0.7f, // 켜짐: 0(밝음), 꺼짐: 0.75(어두움)
            animationSpec = tween(400),
            label = ""
        )

        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colors.white),
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 30.dp)
            ) {
                Text("${uiState.date}, 오늘도 환영해요")
            }
            // 방 이미지
            Image(
                painter = painterResource(R.drawable.img_room),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
            )

            // 창문 토글
            val windowPosX = maxWidth * 0.22f
            val windowPosY = maxHeight * 0.32f
            // 불이 꺼져있으면 창문은 닫혀야 함
            ToggleWindowImage(
                modifier = modifier,
                isLightOn = isLightOn,
                windowPosX = windowPosX,
                windowPosY = windowPosY,
                isLocationGranted = uiState.isLocationPermissionGranted,
                forceOpen = forceOpenWindow,
                weather = weather,
                onClick = {
                    when (ContextCompat.checkSelfPermission(
                        context, Manifest.permission.ACCESS_FINE_LOCATION
                    )) {
                        PermissionChecker.PERMISSION_GRANTED -> {
                            Timber.d("위치 권한 이미 허용됨 ✅")
                            Timber.d("위치 권한: 허용, ${uiState.isLocationPermissionGranted}")
                            // 위치 바로 가져오기
                        }
                        else -> {
                            Timber.d("위치 권한 없음 → 다이얼로그 요청")
                            Timber.d("위치 권한: X, ${uiState.isLocationPermissionGranted}")
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }
                }
            )

            // 책상
            val deskPosX = maxWidth * 0.45f
            val deskPosY = maxHeight * 0.38f
            Image(
                painter = painterResource(R.drawable.img_desk),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = deskPosX, y = deskPosY)
                    .size(200.dp),
            )

            // 다이어리
            val bookPosX = maxWidth * 0.58f
            val bookPosY = maxHeight * 0.4f
            if (isLightOn) {
                Image(
                    painter = painterResource(R.drawable.img_diary),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = bookPosX, y = bookPosY)
                        .size(100.dp)
                        .wobbleClickable(
                            onClick = {
                                navToDiaryList()
                            }
                        ),
                )
            }

            // 화분 테이블
            val tablePosX = maxWidth * 0.1f
            val tablePosY = maxHeight * 0.5f
            Image(
                painter = painterResource(R.drawable.img_table),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = tablePosX, y = tablePosY)
                    .size(100.dp),
            )

            // 화분
            val plantPosX = maxWidth * 0.13f
            val plantPosY = maxHeight * 0.45f
            Image(
                painter = painterResource(R.drawable.img_plant),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = plantPosX, y = plantPosY)
                    .size(80.dp)
                    .wobbleClickable(
                        enabled = isLightOn  // 불 켜졌을 때만 클릭 가능
                    ) {
                        navToPlant()
                    },
            )

            // 화면 밝기 오버레이 (검정 반투명) — 맨 위 스위치/창문 아래에 깔리도록 여기 배치
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color.Black.copy(alpha = dimAlpha))
            )

            // 스위치 (비율 좌표) — 클릭 시 불 토글
            val switchPosX = maxWidth * 0.04f
            val switchPosY = maxHeight * 0.38f
            Image(
                painter = painterResource(R.drawable.img_switch),
                contentDescription = null,
                modifier = Modifier
                    .offset(x = switchPosX, y = switchPosY)
                    .size(70.dp)
                    .wobbleClickable(
                        onClick = {
                            isLightOn = !isLightOn
                        }
                    ),

                )

            // 불이 꺼졌을 때는 마법책이 보임
            if (!isLightOn) {
                Image(
                    painter = painterResource(R.drawable.img_magicbook),
                    contentDescription = null,
                    modifier = Modifier
                        .offset(x = bookPosX, y = bookPosY)
                        .size(100.dp)
                        .wobbleClickable(
                            onClick = {
                                navToMagicBook()
                            }
                        ),

                    )
            }
        }
    }
}


@Preview
@Composable
private fun PreviewMainScreen() {
    ExampleTheme {
        HomeScreen(

        )
    }
}