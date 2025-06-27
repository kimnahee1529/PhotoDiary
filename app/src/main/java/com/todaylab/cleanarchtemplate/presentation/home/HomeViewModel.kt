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
import com.todaylab.cleanarchtemplate.presentation.toDomain
import com.todaylab.cleanarchtemplate.presentation.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

interface HomeEvent {
    fun saveLocation(
        lat: Double,
        lon: Double,
    )

    fun saveBirthDate(
        year: String,
        month: String,
        day: String,
    )
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getBirthDateUseCase: GetBirthDateUseCase,
    private val saveBirthDateUseCase: SaveBirthDateUseCase,
) : BaseViewModel(savedStateHandle), HomeEvent {

    private val _lat = MutableStateFlow<Double?>(null)
    private val _lon = MutableStateFlow<Double?>(null)
    private val _weather = MutableStateFlow<DataResource<WeatherModel>>(DataResource.loading())
    private val _birthDate = MutableStateFlow<DataResource<BirthDateModel>>(DataResource.loading())

    // Directly combine flows to create the stateModel
    val stateModel: StateFlow<HomeStateModel> = combine(
        _weather,
        _birthDate
    ) { weather, birthDate ->
        HomeStateModel(weather = weather, birthDate = birthDate)
    }.stateIn( // Use stateIn to convert the combined flow to a StateFlow
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeStateModel()
    )

    val event: HomeEvent = this@HomeViewModel

    init {
        loadBirthDate()

        // load weather when current location is updated
        viewModelScopeEH.launch {
            combine(_lat, _lon) { lat, lon ->
                lat to lon
            }.collectLatest { (lat, lon) ->
                if (lat == null || lon == null) return@collectLatest
                loadWeather(lat, lon)
            }
        }

        // collect custom exception
        viewModelScopeEH.launch {
            customException.collect {
                Timber.e(it.message)
            }
        }
    }

    private fun loadWeather(
        lat: Double,
        lon: Double,
    ) {
        viewModelScopeEH.launch(Dispatchers.IO) {
            _weather.update {
                DataResource.loading(it.getDataOrNull())
            }
            _weather.update {
                getWeatherUseCase(lat, lon).mapData { it.toPresentation() }
            }
        }
    }

    private fun loadBirthDate() {
        viewModelScopeEH.launch(Dispatchers.IO) {
            _birthDate.update {
                DataResource.loading(it.getDataOrNull())
            }
            _birthDate.update {
                getBirthDateUseCase().mapData { it.toPresentation() }
            }
        }
    }

    override fun saveLocation(
        lat: Double,
        lon: Double,
    ) {
        Timber.d("save location - $lat, $lon")
        _lat.update { lat }
        _lon.update { lon }
    }

    override fun saveBirthDate(
        year: String,
        month: String,
        day: String,
    ) {
        val newBirthDate = BirthDateModel(year, month, day)
        _birthDate.update {
            DataResource.success(newBirthDate)
        }
        viewModelScope.launch(Dispatchers.IO) {
            saveBirthDateUseCase(newBirthDate.toDomain())
        }
    }
}