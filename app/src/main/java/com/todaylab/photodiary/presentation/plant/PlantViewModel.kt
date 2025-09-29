package com.todaylab.photodiary.presentation.plant

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.core.UserPreferenceManager
import com.todaylab.photodiary.domain.usecase.GetSolutionByMagicBookUseCase
import com.todaylab.photodiary.presentation.BaseViewModel
import com.todaylab.photodiary.presentation.plant.PlantViewModel.PlantUiState
import com.todaylab.photodiary.presentation.diary.TimeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject


@HiltViewModel
class PlantViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPrefs: UserPreferenceManager,
    private val getSolutionUseCase: GetSolutionByMagicBookUseCase,
) : BaseViewModel<PlantUiState>(savedStateHandle) {

    private val today = LocalDate.now().toString()
    private val _solution = MutableStateFlow<String>("처음해답")
    private val _roomId = MutableStateFlow<Int?>(-1)
    private val _streak = MutableStateFlow(0)

    private val _events = MutableSharedFlow<PlantUiState>()
    val events: SharedFlow<PlantUiState> = _events

    override val uiState: StateFlow<PlantUiState> =
        combine(
            _streak, _roomId
        ) { streak, roomId ->
            PlantUiState(
                _streak = streak,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = PlantUiState()
        )


    init {
        viewModelScope.launch {
//            userPrefs.addWateringDates()
            userPrefs.getWateringDates().collectLatest { dates ->
                _streak.value = calculateStreak(dates)
            }
        }
    }

    private fun calculateStreak(dates: Set<String>): Int {
        val today = LocalDate.now()
        var streak = 0
        for (i in 0 until 5) {
            val day = today.minusDays(i.toLong()).toString()
            if (dates.contains(day)) {
                streak++
            } else break
        }
        return streak
    }

    fun addWatering() {
        viewModelScope.launch {
            userPrefs.addWateringDate()
        }
    }


    data class PlantUiState(
        val _streak: Int = 0
    )

}


sealed class PlantEvent {
    data class OpenDatePicker(val initial: LocalDate) : PlantEvent()
    data class OpenTimePicker(val type: TimeType, val initial: LocalTime) : PlantEvent()
}