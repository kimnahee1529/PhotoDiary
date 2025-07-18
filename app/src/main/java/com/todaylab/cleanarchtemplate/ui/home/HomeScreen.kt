package com.todaylab.cleanarchtemplate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.presentation.home.HomeEvent
import com.todaylab.cleanarchtemplate.presentation.home.HomeViewModel
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState
import com.todaylab.cleanarchtemplate.ui.model.HomeScreenState
import com.todaylab.cleanarchtemplate.ui.model.WeatherState
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.toUi
import com.todaylab.cleanarchtemplate.ui.widget.CloverButton
import timber.log.Timber
import java.util.Date
import kotlinx.coroutines.launch



@Composable
fun HomeRoute(
    navToResult: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val homeModel by viewModel.screenModel.collectAsState()
    val homeState = remember(homeModel) { homeModel.toUi() }

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
    homeState: HomeScreenState,
    homeEvent: HomeEvent,
    navToResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val (year, setYear) = remember {
        mutableStateOf(homeState.birthDate.getDataOrNull()?.year ?: "")
    }
    val (month, setMonth) = remember {
        mutableStateOf(homeState.birthDate.getDataOrNull()?.month ?: "")
    }
    val (day, setDay) = remember {
        mutableStateOf(homeState.birthDate.getDataOrNull()?.day ?: "")
    }

    Timber.e("날짜 들어있음?  year:$year month:$month day:$day")

    Scaffold(
        modifier = modifier.background(color = MaterialTheme.colors.white),
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier
                                .padding(top = 10.dp),
                            text = stringResource(id = R.string.lucky_introduction),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colors.text1
                        )
                    }
                },
            )
        },
//        bottomBar = {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                contentAlignment = Alignment.Center
//            ) {
//                CloverButton(
//                    modifier = Modifier
//                        .padding(bottom = 60.dp),
//                    text = "행운 찾기",
//                    enabled = (year.isNotBlank() && month.isNotBlank() && day.isNotBlank()),
//                    onClick = {
//                        if (year.isBlank() || month.isBlank() || day.isBlank()) {
//                            coroutineScope.launch {
//                                snackbarHostState.showSnackbar("생년월일을 모두 입력해 주세요.")
//                            }
//                        } else {
//                            homeEvent.saveBirthDateInput(year, month, day)
//                            navToResult()
//                        }
//                    },
//                )
//            }
//        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CurrentWeatherIcon(
                weather = homeState.weather,
                shouldRequestLocationPermission = true,
                saveLocation = homeEvent::saveLocation,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .align(Alignment.TopEnd)
            )
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(30.dp),
            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.lucky),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .width(200.dp),
//                )
                BirthDateInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    year = year,
                    month = month,
                    day = day,
                    onYearSelect = setYear,
                    onMonthSelect = setMonth,
                    onDaySelect = setDay,
                )
            }
            CloverButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                text = "행운 찾기",
                enabled = (year.isNotBlank() && month.isNotBlank() && day.isNotBlank()),
                onClick = {
                    if (year.isBlank() || month.isBlank() || day.isBlank()) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar("생년월일을 모두 입력해 주세요.")
                        }
                    } else {
                        homeEvent.saveBirthDateInput(year, month, day)
                        navToResult()
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewMainScreen() {
    LuckyTheme {
        HomeScreen(
            homeState = HomeScreenState(
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
                override fun saveBirthDateInput(year: String, month: String, day: String) {}
            },
            navToResult = {},
        )
    }
}