package com.todaylab.photodiary.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.core.CustomException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel<UiState>(
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    abstract val uiState: StateFlow<UiState>

    private val _customException = MutableSharedFlow<CustomException>()
    protected val customException = _customException.asSharedFlow()

    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            viewModelScope.launch {
                throwable.let {
                    if (it is CustomException) {
                        it
                    } else {
                        CustomException(
                            code = CustomException.StatusCode.Unknown,
                            message = throwable.message ?: "",
                        )
                    }.let { customException ->
                        _customException.emit(customException)
                    }
                }
            }
        }
    protected val viewModelScopeEH = viewModelScope + exceptionHandler
}

