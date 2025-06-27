package com.todaylab.cleanarchtemplate.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.CustomException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel(
    val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _customExceptions: MutableSharedFlow<CustomException> = MutableSharedFlow()
    protected val customException = _customExceptions.asSharedFlow()

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
                        _customExceptions.emit(customException)
                    }
                }
            }
        }

    protected val viewModelScopeEH = viewModelScope + exceptionHandler
}
