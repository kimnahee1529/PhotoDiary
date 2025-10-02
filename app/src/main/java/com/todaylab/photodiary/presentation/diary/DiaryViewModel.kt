package com.todaylab.photodiary.presentation.diary

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.domain.impl.GetDiaryListUseCase
import com.todaylab.photodiary.domain.impl.GetDiaryUseCase
import com.todaylab.photodiary.domain.impl.SaveDiaryUseCase
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.domain.model.WeatherType
import com.todaylab.photodiary.presentation.BaseViewModel
import com.todaylab.photodiary.presentation.diary.DiaryViewModel.DiaryUiState
import com.todaylab.photodiary.presentation.mapper.DiaryMapper
import com.todaylab.photodiary.presentation.model.DiaryModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class DiaryViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val saveDiary: SaveDiaryUseCase,
    private val getDiaryUseCase: GetDiaryUseCase,
    private val getDiaryListUseCase: GetDiaryListUseCase,
) : BaseViewModel<DiaryUiState>(savedStateHandle) {

    private val _roomId = MutableStateFlow<Int?>(-1)
    private val _successCreateRoom = MutableStateFlow<Boolean>(false)
    private val _selectedPhotos = MutableStateFlow<List<Uri>>(emptyList())
    private val _selectedDate = MutableStateFlow(LocalDate.now())
    private val _selectedWakeTime = MutableStateFlow(LocalTime.of(7, 0))
    private val _selectedBedTime = MutableStateFlow(LocalTime.of(22, 0))
    private val _diaryList = MutableStateFlow<List<Diary>>(emptyList())

    private val _events = MutableSharedFlow<DiaryEvent>()
    val events: SharedFlow<DiaryEvent> = _events

    override val uiState: StateFlow<DiaryUiState> =
        combine(
            _roomId,
            _successCreateRoom,
            _selectedPhotos,
            _selectedDate
        ) { roomId, successCreateRoom, selectedPhotos, selectedDate ->
            Partial(roomId, successCreateRoom, selectedPhotos, selectedDate)
        }.let { partialFlow ->
            combine(
                partialFlow,
                _selectedWakeTime,
                _selectedBedTime,
                _diaryList
            ) { p, wakeTime, bedTime, diaryList ->
                DiaryUiState(
                    roomId = p.roomId,
                    successCreateRoom = p.successCreateRoom,
                    selectedPhotos = p.selectedPhotos,
                    selectedDate = p.selectedDate,
                    selectedWakeTime = wakeTime,
                    selectedBedTime = bedTime,
                    diaryList = diaryList
                )
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = DiaryUiState()
        )

    init {
        loadDiaryList()
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
            // TODO 확장함수로 변환
            saveDiary(DiaryMapper.mapToHigh(newDiary))
        }
    }

    fun loadDiaryList() {
        viewModelScope.launch {
            try {
                val diaries = getDiaryListUseCase()
                _diaryList.value = diaries
                Timber.e("다이어리 리스트: ${diaries}")
            } catch (e: Exception) {
                Timber.e("Error loading diaries: ${e.message}")
            }
        }
    }

    // 다이얼로그
    fun updateSelectedPhotos(photos: List<Uri>) {
        _selectedPhotos.value = photos
    }

    fun onClickDateCell() = viewModelScope.launch {
        _events.emit(DiaryEvent.OpenDatePicker(initial = _selectedDate.value))
    }

    fun onDatePicked(date: LocalDate) {
        _selectedDate.value = date
    }

    fun onClickWakeTime() = viewModelScope.launch {
        _events.emit(DiaryEvent.OpenTimePicker(TimeType.Wake, _selectedWakeTime.value))
    }

    fun onClickBedTime() = viewModelScope.launch {
        _events.emit(DiaryEvent.OpenTimePicker(TimeType.Bed, _selectedBedTime.value))
    }

    fun onTimePicked(type: TimeType, time: LocalTime) {
        when (type) {
            TimeType.Wake -> _selectedWakeTime.value = time
            TimeType.Bed -> _selectedBedTime.value = time
        }
    }

    data class DiaryUiState(
        val roomId: Int? = null,
        val successCreateRoom: Boolean = false,
        val selectedPhotos: List<Uri> = emptyList(),
        val selectedDate: LocalDate = LocalDate.now(),
        val selectedWakeTime: LocalTime = LocalTime.of(7, 0),
        val selectedBedTime: LocalTime = LocalTime.of(22, 0),
        val diaryList: List<Diary> = emptyList(),
    )

}

private data class Partial(
    val roomId: Int?,
    val successCreateRoom: Boolean,
    val selectedPhotos: List<Uri>,
    val selectedDate: LocalDate,
)

sealed class DiaryEvent {
    data class OpenDatePicker(val initial: LocalDate) : DiaryEvent()
    data class OpenTimePicker(val type: TimeType, val initial: LocalTime) : DiaryEvent()
}

enum class TimeType { Wake, Bed }