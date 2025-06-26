package com.todaylab.cleanarchtemplate.presentation.home

import androidx.lifecycle.SavedStateHandle
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

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getBirthDateUseCase: GetBirthDateUseCase,
    private val saveBirthDateUseCase: SaveBirthDateUseCase,
) : BaseViewModel(savedStateHandle),
    HomeEvent {

    private val _lat = MutableStateFlow<Double?>(null)
    private val _lon = MutableStateFlow<Double?>(null)
    private val _weather = MutableStateFlow<DataResource<WeatherModel>>(DataResource.loading())
    private val _birthDate =
        MutableStateFlow<DataResource<BirthDateModel>>(DataResource.loading())

    private val _stateModel = MutableStateFlow<HomeStateModel>(HomeStateModel())
    val stateModel: StateFlow<HomeStateModel> = _stateModel.asStateFlow()

    init {
        viewModelScopeEH.launch {
            customException.collect {
                Timber.e(it.message)
            }
        }

        viewModelScopeEH.launch(Dispatchers.IO) {
            // load saved birth date on init
            loadBirthDate()
        }

        viewModelScopeEH.launch {
            // todo: get current location from gps
            _lat.update { 37.5 }
            _lon.update { 127.0 }
        }

        viewModelScopeEH.launch(Dispatchers.IO) {
            // load weather when current location is set
            combine(_lat, _lon) { lat, lon ->
                if (lat != null && lon != null) {
                    loadWeather(lat, lon)
                }
            }
        }

        viewModelScopeEH.launch {
            // update home state model
            combine(_weather, _birthDate) { weather, birthDate ->
                _stateModel.update {
                    it.copy(
                        weather = weather,
                        birthDate = birthDate,
                    )
                }
            }
        }
    }

    private suspend fun loadWeather(
        lat: Double,
        lon: Double,
    ) {
        /// 1. set weather to loading state
        _weather.update {
            DataResource.loading(
                when (it) {
                    is DataResource.Success -> (it.data)
                    is DataResource.Loading -> (it.data)
                    is DataResource.Error -> null
                }
            )
        }
        //  2. update weather state
        _weather.update {
            when (val newWeather = getWeatherUseCase(lat, lon)) {
                is DataResource.Success -> DataResource.success(newWeather.data.toPresentation())
                is DataResource.Loading -> DataResource.loading(newWeather.data?.toPresentation())
                is DataResource.Error -> DataResource.error(newWeather.throwable)
            }
        }
    }

    private suspend fun loadBirthDate() {
        /// 1. set birth date to loading state
        _birthDate.update {
            DataResource.loading(
                when (it) {
                    is DataResource.Success -> (it.data)
                    is DataResource.Loading -> (it.data)
                    is DataResource.Error -> null
                }
            )
        }
        // 2. update birth date state
        _birthDate.update {
            when (val savedBirthDate = getBirthDateUseCase()) {
                is DataResource.Success -> DataResource.success(savedBirthDate.data.toPresentation())
                is DataResource.Loading -> DataResource.loading(savedBirthDate.data?.toPresentation())
                is DataResource.Error -> DataResource.error(savedBirthDate.throwable)
                else -> DataResource.error(Throwable("presentation layer error - Unknown error"))
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
