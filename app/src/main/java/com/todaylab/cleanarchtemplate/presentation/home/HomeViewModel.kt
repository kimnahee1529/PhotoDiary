package com.todaylab.cleanarchtemplate.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherByLocationUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import com.todaylab.cleanarchtemplate.presentation.mapper.BirthDateMapper
import com.todaylab.cleanarchtemplate.presentation.mapper.WeatherMapper
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeScreenModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
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

    fun saveBirthDateInput(
        year: String,
        month: String,
        day: String,
    )
}

@HiltViewModel
class HomeViewModel
@Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getWeatherByLocation: GetWeatherByLocationUseCase,
    private val getBirthDate: GetBirthDateUseCase,
    private val saveBirthDate: SaveBirthDateUseCase,
) : BaseViewModel<HomeScreenModel>(savedStateHandle), HomeEvent {

    private val _lat = MutableStateFlow<Double?>(null)
    private val _lon = MutableStateFlow<Double?>(null)
    private val _weather = MutableStateFlow<DataResource<WeatherModel>>(DataResource.loading())
    private val _birthDate = MutableStateFlow<DataResource<BirthDateModel>>(DataResource.loading())

    // Directly combine flows to create the stateModel
    override val screenModel: StateFlow<HomeScreenModel> = combine(
        _weather,
        _birthDate
    ) { weather, birthDate ->
        HomeScreenModel(weather = weather, birthDate = birthDate)
    }.stateIn( // Use stateIn to convert the combined flow to a StateFlow
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeScreenModel()
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
                getWeatherByLocation(lat, lon).mapData(WeatherMapper::mapToLow)
            }
        }
    }

    private fun loadBirthDate() {
        viewModelScopeEH.launch(Dispatchers.IO) {
            _birthDate.update {
                DataResource.loading(it.getDataOrNull())
            }
            _birthDate.update {
                getBirthDate().mapData(BirthDateMapper::mapToLow)
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

    override fun saveBirthDateInput(
        year: String,
        month: String,
        day: String,
    ) {
        val newBirthDate = BirthDateModel(year, month, day)
        _birthDate.update {
            DataResource.success(newBirthDate)
        }
        viewModelScope.launch(Dispatchers.IO) {
            saveBirthDate(BirthDateMapper.mapToHigh(newBirthDate))
        }
    }
}