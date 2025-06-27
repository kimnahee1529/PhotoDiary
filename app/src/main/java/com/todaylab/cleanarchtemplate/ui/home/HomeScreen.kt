package com.todaylab.cleanarchtemplate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.presentation.home.HomeEvent
import com.todaylab.cleanarchtemplate.presentation.home.HomeViewModel
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeState
import com.todaylab.cleanarchtemplate.ui.model.WeatherState
import com.todaylab.cleanarchtemplate.ui.toUi
import com.todaylab.cleanarchtemplate.ui.widget.InputCompleteButton
import com.todaylab.cleanarchtemplate.ui.widget.WheelSpinner
import java.util.Date

data class SpinnerState(
    val label: String,
    val items: List<String>,
    val onSelected: (String) -> Unit,
)

@Composable
fun HomeRoute(
    navToResult: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeStateModel by viewModel.stateModel.collectAsState()
    val homeState = remember(homeStateModel) { homeStateModel.toUi() }

    HomeScreen(
        homeState = homeState,
        homeEvent = viewModel.event,
        navToResult = navToResult,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeState: HomeState,
    homeEvent: HomeEvent,
    navToResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    var year by remember { mutableStateOf("") }
    var month by remember { mutableStateOf("") }
    var day by remember { mutableStateOf("") }

    // Dialog 상태
    var spinnerDialogState by remember {
        mutableStateOf<SpinnerState?>(null)
    }

    Scaffold(
        modifier = modifier.background(color = Color.White),
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("오늘 행운을 찾으러 가봐요!")
                        Spacer(modifier = Modifier.weight(1f))
                        CurrentWeatherIcon(
                            weather = homeState.weather,
                            shouldRequestLocationPermission = true,
                            saveLocation = homeEvent::saveLocation,
                            modifier = Modifier
                                .width(120.dp)
                                .height(120.dp)
                        )
                    }
                },
            )
        },
        bottomBar = {
            InputCompleteButton(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                    .navigationBarsPadding(),
                text = "확인하기",
                onNextClick = {
                    homeEvent.saveBirthDate(year, month, day)
                    navToResult()
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Image(
                painter = painterResource(id = R.drawable.lucky),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            )

//            BirthDateInput(
//                year = year,
//                month = month,
//                day = day,
//                onYearChange = { year = it },
//                onMonthChange = { month = it },
//                onDayChange = { day = it },
//            )

            // 생년월일 WheelSpinner 텍스트필드
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                WheelSpinnerSelector(
                    modifier = Modifier.weight(2f),
                    savedText = homeState.birthDate.getDataOrNull()?.year ?: "",
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
                    savedText = homeState.birthDate.getDataOrNull()?.month ?: "",
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
                    savedText = homeState.birthDate.getDataOrNull()?.day ?: "",
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
                        },
                    )
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMainScreen() {
    HomeScreen(
        homeState = HomeState(
            weather = DataResource.success(
                WeatherState(
                    date = Date(),
                    lat = 0.0,
                    lon = 0.0,
                    main = "clear",
                    description = "clear sky",
                    icon = "02d",
                )
            ),
            birthDate = DataResource.success(
                BirthDateState("1999", "12", "14")
            ),
        ),
        homeEvent = object : HomeEvent {
            override fun saveLocation(lat: Double, lon: Double) {}
            override fun saveBirthDate(year: String, month: String, day: String) {}
        },
        navToResult = {},
    )
}
