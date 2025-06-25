package com.todaylab.cleanarchtemplate.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.presentation.home.HomeViewModel
import com.todaylab.cleanarchtemplate.presentation.navigation.AppRoute
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeState
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.toUi
import com.todaylab.cleanarchtemplate.ui.widget.InputCompleteButton
import com.todaylab.cleanarchtemplate.ui.widget.WheelSpinner

data class SpinnerState(
    val label: String,
    val items: List<String>,
    val onSelected: (String) -> Unit
)

@Composable
fun HomeRoute(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val homeStateModel by viewModel.stateModel.collectAsState()
    val homeState = remember(homeStateModel) { homeStateModel.toUi() }

    var isPermissionGranted by remember { mutableStateOf(false) }
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            Log.e("isGranted 권한", isGranted.toString())
            if (isGranted) {
                isPermissionGranted = true
//                viewModel.fetchWeatherWithCurrentLocation(context)
            }
        }
    )

    LaunchedEffect(Unit) {
        isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!isPermissionGranted) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
//            viewModel.fetchWeatherWithCurrentLocation(context)
        }
    }

    HomeScreen(
        homeState = homeState,
        isPermissionGranted = isPermissionGranted,
        onNextClick = { year, month, day ->
//            viewModel.saveBirthDate(year = year, month = month, day = day)
        },
        onNavigate = { route ->
            navController.navigate(route)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    isPermissionGranted: Boolean,
    onNextClick: (String, String, String) -> Unit,
    onNavigate: (String) -> Unit,
) {
    // 생년월일 선택 상태
    var year by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }

    LaunchedEffect(year) {
        Log.e("확인", "year: $year, month: $month, day: $day")
    }
    // Dialog 상태
    var spinnerDialogState by remember {
        mutableStateOf<SpinnerState?>(null)
    }

    Scaffold(
        modifier = Modifier.background(color = Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Row {
                        Text("오늘 행운을 찾으러 가봐요!")
                        Spacer(modifier = Modifier.weight(1f))
                        when {
                            homeState.weather == null -> Text("No weather")
                            homeState.weather.isLoading -> Text("Loading...")
                            homeState.weather.icon != null -> Text("Weather: ${homeState.weather.icon}")
                            homeState.weather.errorMessage != null -> Text("Error: ${homeState.weather.errorMessage}")
                        }
                    }
                }
            )
        },
        bottomBar = {
            InputCompleteButton(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .navigationBarsPadding(),
                text = "확인하기",
                onNextClick = {
                    onNextClick(year, month, day)
                    onNavigate(AppRoute.LuckyResult.route)
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Image(
                painter = painterResource(id = R.drawable.lucky),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            // 생년월일 WheelSpinner 텍스트필드
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                WheelSpinnerSelector(
                    modifier = Modifier.weight(2f),
                    savedText = homeState.birthDate.year,
                    selectedText = year,
                    label = "년",
                    onClick = {
                        spinnerDialogState = SpinnerState(
                            label = "년",
                            items = listOf(
                                "",
                                "",
                                * (1900..2024).map { it.toString() }.toTypedArray(),
                                "",
                                ""
                            ),
                            onSelected = { year = it }
                        )
                    }
                )

                WheelSpinnerSelector(
                    modifier = Modifier.weight(1f),
                    savedText = homeState.birthDate.month,
                    selectedText = month,
                    label = "월",
                    onClick = {
                        spinnerDialogState = SpinnerState(
                            label = "월",
                            items = listOf(
                                "",
                                "",
                                * (1..12).map { it.toString() }.toTypedArray(),
                                "",
                                ""
                            ),
                            onSelected = { month = it }
                        )
                    }
                )

                WheelSpinnerSelector(
                    modifier = Modifier.weight(1f),
                    savedText = homeState.birthDate.day,
                    selectedText = day,
                    label = "일",
                    onClick = {
                        spinnerDialogState = SpinnerState(
                            label = "일",
                            items = listOf(
                                "",
                                "",
                                * (1..31).map { it.toString() }.toTypedArray(),
                                "",
                                ""
                            ),
                            onSelected = { day = it }
                        )
                    }
                )
            }

            Text("메인 화면 - 권한 허용 ${if (isPermissionGranted) "O" else "X"}")

//            Text(
//                text = "오늘 행운 템플릿 앱입니다~",
//                fontSize = 140.sp
//            )
        }
    }

    // 실제 다이얼로그는 최하단에서 그려야 안전
    spinnerDialogState?.let { state ->
        CenteredButtonDialog(
            onDismissRequest = { spinnerDialogState = null },
            onConfirm = { spinnerDialogState = null },
            content = {
                WheelSpinner(
                    items = state.items,
                    onSelected = {
                        state.onSelected(it)
                    }
                )
            }
        )
    }

}


@Composable
fun WheelSpinnerSelector(
    modifier: Modifier = Modifier,
    savedText: String,
    selectedText: String,
    label: String = "",
    onClick: () -> Unit
) {
    Row(modifier = modifier) {

        Box(
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
                .background(color = MaterialTheme.colors.grey2, shape = RoundedCornerShape(6.dp))
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = if (selectedText.isNotBlank()) selectedText else if (savedText.isNotBlank()) savedText else "",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = if (selectedText.isNotBlank()) Color.Black else Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.align(Alignment.Bottom)
        )

    }
}

@Composable
fun CenteredButtonDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                content()

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onConfirm) {
                    Text("확인")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    HomeScreen(
        homeState = HomeState(
            weather = null,
            birthDate = BirthDateState(year = "1999", month = "12", day = "14")
        ),
        isPermissionGranted = true,
        onNextClick = { _, _, _ -> },
        onNavigate = {}
    )
}

@Composable
fun ExampleScreen() {
    val yearItems = (1980..2025).map { it.toString() }

    WheelSpinnerSelector(
        savedText = "2023",
        selectedText = "2023",
        label = "년",
        onClick = {}
    )
}

@Preview
@Composable
fun PreviewExampleScreen() {
    ExampleScreen()
}
