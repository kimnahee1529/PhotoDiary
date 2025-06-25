package com.todaylab.cleanarchtemplate.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeStateModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
import com.todaylab.cleanarchtemplate.presentation.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface HomeEvent {
    fun saveLocation(
        lat: Double,
        lon: Double,
    )

    fun setBirthDateYear(year: String)

    fun setBirthDateMonth(month: String)

    fun setBirthDateDay(day: String)

    fun saveBirthDate()
}

// todo: implement BaseViewModel class
@HiltViewModel
class HomeViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveBirthDateUseCase: SaveBirthDateUseCase,
    private val getBirthDateUseCase: GetBirthDateUseCase,
) : BaseViewModel(savedStateHandle),
    HomeEvent {
    private val _weatherModel = MutableStateFlow<DataResource<WeatherModel>>(DataResource.loading())
    private val _birthDateState = MutableStateFlow<BirthDateModel>(BirthDateModel())

    private val _stateModel = MutableStateFlow<HomeStateModel>(HomeStateModel())
    val stateModel: StateFlow<HomeStateModel> = _stateModel.asStateFlow()

    init {
        viewModelScopeEH.launch {
            customException.collect {
                Timber.e(it.message)
            }
            }

        viewModelScope.launch(Dispatchers.IO) {
//                loadBirthDate()
        }
        viewModelScope.launch(Dispatchers.IO) {
            // todo load weather when current location is set
            loadWeather(lat = 37.5, lon = 127.0)
        }

        combine(_weatherModel, _birthDateState) { weather, birthDate ->
            _stateModel.update {
                it.copy(
                    weather = weather,
                    birthDate = birthDate,
                )
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun loadWeather(
        lat: Double,
        lon: Double,
    ) {
        _weatherModel.update { it ->
            when (it) {
                is DataResource.Success -> DataResource.success(it.data)
                is DataResource.Loading -> DataResource.loading(it.data)
                is DataResource.Error -> DataResource.loading()
            }
        }
                val weather = getWeatherUseCase(lat, lon)
        Timber.d("weather use case - weather: $weather")
                _weatherModel.update {
                    when (weather) {
                        is DataResource.Success -> DataResource.success(weather.data.toPresentation())
                        is DataResource.Loading -> DataResource.loading(weather.data?.toPresentation())
                        is DataResource.Error -> DataResource.error(weather.throwable)
                    }
                }
    }

    private suspend fun loadBirthDate() {
        viewModelScope.launch {
            _birthDateState.update { it.copy(isLoading = true) }

            val savedBirthDate = getBirthDateUseCase()
            _birthDateState.update {
                it.copy(
                    isLoading = false,
                    year = savedBirthDate?.year ?: "",
                    month = savedBirthDate?.month ?: "",
                    day = savedBirthDate?.day ?: "",
                )
            }
        }
    }

    override fun saveLocation(
        lat: Double,
        lon: Double,
    ) {
        TODO("Not yet implemented")
    }

    override fun setBirthDateYear(year: String) {
        TODO("Not yet implemented")
    }

    override fun setBirthDateMonth(month: String) {
        TODO("Not yet implemented")
    }

    override fun setBirthDateDay(day: String) {
        TODO("Not yet implemented")
    }

    override fun saveBirthDate() {
        TODO("Not yet implemented")
//        viewModelScope.launch {
//            saveBirthDateUseCase(
//                _birthDateState.value // todo: map to domain
//            )
//        }
    }
}
