package com.todaylab.cleanarchtemplate.presentation.home

import androidx.lifecycle.SavedStateHandle
import com.todaylab.cleanarchtemplate.domain.usecase.GetCurrentWeatherUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * presentation layer
 * home view model
 */

/**
 * home screen states
 */
data class HomeState(
    // device location used to get weather
    val lat: Double? = null,
    val long: Double? = null,

    // weather state from current location
    //val weather: WeatherState? = null,

    // birth date
    val birth: String = "",
    // save birth to datastore if set to true
    val saveBirth: Boolean = false,
)

/**
 * home screen event interface
 */
interface HomeEvent {
    /**
     *
     */
    fun onToggleSaveBirth(toggle: Boolean)

    /**
     *
     */
    fun onCompletedBirthInput(birth: String)
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
) : BaseViewModel(savedStateHandle) {
    // todo: implement home viewmodel
}