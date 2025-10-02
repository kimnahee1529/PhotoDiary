package com.todaylab.photodiary.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.core.UserPreferenceManager
import com.todaylab.photodiary.domain.impl.GetWeatherByLocationUseCase
import com.todaylab.photodiary.domain.impl.SaveDiaryUseCase
import com.todaylab.photodiary.domain.model.WeatherType
import com.todaylab.photodiary.presentation.BaseViewModel
import com.todaylab.photodiary.presentation.diary.TimeType
import com.todaylab.photodiary.presentation.home.HomeViewModel.HomeUiState
import com.todaylab.photodiary.presentation.mapper.DiaryMapper
import com.todaylab.photodiary.presentation.mapper.WeatherMapper
import com.todaylab.photodiary.presentation.model.DiaryModel
import com.todaylab.photodiary.ui.mapper.toState
import com.todaylab.photodiary.ui.model.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject


@HiltViewModel
class HomeViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeatherByLocation: GetWeatherByLocationUseCase,
    private val saveDiary: SaveDiaryUseCase,
    private val userPreferenceManager: UserPreferenceManager,
) : BaseViewModel<HomeUiState>(savedStateHandle) {

    private val _roomId = MutableStateFlow<Int?>(-1)

    //    private val _successCreateRoom = MutableStateFlow<Boolean>(false)
//    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())
//    private val _selectedDate = MutableStateFlow(LocalDate.now())
//    private val _selectedWakeTime = MutableStateFlow(LocalTime.of(7, 0))
//    private val _selectedBedTime = MutableStateFlow(LocalTime.of(22, 0))
    private val _isLocationPermissionGranted = MutableStateFlow(false)
    private val _lat = MutableStateFlow<Double?>(null)
    private val _lon = MutableStateFlow<Double?>(null)
    private val _weather = MutableStateFlow(WeatherState(WeatherType.ETC))
    private val _date = MutableStateFlow<String>("")

    private val _events = MutableSharedFlow<HomeEvent>()
    val events: SharedFlow<HomeEvent> = _events

    override val uiState: StateFlow<HomeUiState> =
        combine(
            _roomId,
            _date
        ) { roomId, date ->
            Partial(roomId, date)
        }.let { partialFlow ->
            combine(
                partialFlow,
                _isLocationPermissionGranted,
                _weather,
                _date
            ) { p, isLocationPermissionGranted, weather, date ->
                HomeUiState(
                    roomId = p.roomId,
                    isLocationPermissionGranted = isLocationPermissionGranted,
                    weather = weather,
                    date = date
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )


    init {
        viewModelScopeEH.launch {
            getDate()
            combine(_lat, _lon) { lat, lon ->
                lat to lon
            }.collectLatest { (lat, lon) ->
                if (lat == null || lon == null) return@collectLatest
                loadWeather(lat, lon)
            }
        }
    }

    fun saveLocationPermissionGranted(granted: Boolean) {
        Timber.d("viewModel 위치 권한: $granted")
        viewModelScope.launch {
            userPreferenceManager.saveLocationPermissionGranted(granted)
            _isLocationPermissionGranted.value = granted
        }
    }

    fun loadLocationPermissionGranted() {
        viewModelScope.launch {
            userPreferenceManager.readLocationPermissionGranted()
                .collect { granted ->
                    _isLocationPermissionGranted.value = granted
                }
        }
    }

    fun getDate() {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN)
        val formatted = today.format(formatter)
        _date.value = formatted
    }

    fun saveLocation(
        lat: Double,
        lon: Double,
    ) {
        Timber.d("save location - $lat, $lon")
        _lat.update { lat }
        _lon.update { lon }
    }

    fun loadWeather(
        lat: Double,
        lon: Double,
    ) {
//        viewModelScopeEH.launch(Dispatchers.IO) {
////            _weather.update {
////                DataResource.loading(it.getDataOrNull())
////            }
//            val result = getWeatherByLocation(lat, lon)
//                .mapData(WeatherMapper::mapToLow) // WeatherModel
//
//            val state = result.getDataOrNull()?.toState()
//                ?: WeatherState(WeatherType.ETC) // 실패 시 기본값
//
//            _weather.value = state
//        }
    }

    fun saveDiary(
        id: Long = 0L,
        title: String,
        date: LocalDate,
        weather: WeatherType,
        wakeTime: LocalTime,
        sleepTime: LocalTime,
        content: String,
        photoUris: List<String>
    ) {
        val newDiary = DiaryModel(id, title, date, weather, wakeTime, sleepTime, content, photoUris)
        viewModelScope.launch(Dispatchers.IO) {
            saveDiary(DiaryMapper.mapToHigh(newDiary))
        }
    }

    data class HomeUiState(
        val roomId: Int? = null,
        val isLocationPermissionGranted: Boolean = false,
        val weather: WeatherState = WeatherState(WeatherType.ETC),
        val date: String = "",
    )

}

private data class Partial(
    val roomId: Int?,
    val date: String,
)

sealed class HomeEvent {
    data class OpenDatePicker(val initial: LocalDate) : HomeEvent()
    data class OpenTimePicker(val type: TimeType, val initial: LocalTime) : HomeEvent()
}