package com.todaylab.photodiary.presentation.magic

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.core.UserPreferenceManager
import com.todaylab.photodiary.domain.impl.GetSolutionByMagicBookUseCase
import com.todaylab.photodiary.presentation.BaseViewModel
import com.todaylab.photodiary.presentation.diary.TimeType
import com.todaylab.photodiary.presentation.magic.MagicBookViewModel.MagicBookUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
class MagicBookViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val userPrefs: UserPreferenceManager,
    private val getSolutionUseCase: GetSolutionByMagicBookUseCase,
) : BaseViewModel<MagicBookUiState>(savedStateHandle) {

    private val today = LocalDate.now().toString()
    private val _solution = MutableStateFlow<String>("처음해답")
    private val _roomId = MutableStateFlow<Int?>(-1)

    private val _events = MutableSharedFlow<MagicBookEvent>()
    val events: SharedFlow<MagicBookEvent> = _events

    override val uiState: StateFlow<MagicBookUiState> =
        combine(
            _solution, _roomId
        ) { solution, roomId ->
            MagicBookUiState(
                solution = solution,
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = MagicBookUiState()
        )

    init {
        viewModelScope.launch {
            userPrefs.readLastSolution().collect { (lastDate, lastSolution) ->
                if (lastDate == today && lastSolution != null) {
                    // 오늘 이미 본 해답 → 저장된 값 그대로 사용
                    _solution.value = lastSolution
                } else {
                    // 아직 안 본 상태 → 빈 문자열 유지 (Idle 상태)
                    _solution.value = ""
                }
            }
        }
    }

    suspend fun getSolution() {
        val solution = getSolutionUseCase.invoke()
        userPrefs.saveTodaySolution(today, solution)
        _solution.value = solution
        Timber.d("가져온 solution: $solution")
    }

    data class MagicBookUiState(
        val solution: String = ""
    )

}


sealed class MagicBookEvent {
    data class OpenDatePicker(val initial: LocalDate) : MagicBookEvent()
    data class OpenTimePicker(val type: TimeType, val initial: LocalTime) : MagicBookEvent()
}