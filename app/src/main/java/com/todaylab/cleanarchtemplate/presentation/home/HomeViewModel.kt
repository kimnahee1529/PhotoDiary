package com.todaylab.cleanarchtemplate.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetWeatherUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.SaveBirthDateUseCase
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.HomeStateModel
import com.todaylab.cleanarchtemplate.presentation.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface HomeEvent {
    fun saveLocation(lat: Double, lon: Double)
    fun saveBirthDate(year: String, month: String, day: String)
}

// todo: implement BaseViewModel class
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val saveBirthDateUseCase: SaveBirthDateUseCase,
    private val getBirthDateUseCase: GetBirthDateUseCase
) : ViewModel(), HomeEvent {

    private val _weatherModel = MutableStateFlow<WeatherModel?>(null)
    private val _birthDateState = MutableStateFlow<BirthDateModel>(BirthDateModel())

    private val _stateModel = MutableStateFlow<HomeStateModel>(HomeStateModel())
    val stateModel: StateFlow<HomeStateModel> = _stateModel.asStateFlow()

    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
//                loadWeather(lat = 37.5, lon = 127.0)
            }
        }

        combine(_weatherModel, _birthDateState) { weather, birthDate ->
            _stateModel.update {
                it.copy(
                    weather = weather,
                    birthDate = birthDate
                )
            }
        }.launchIn(viewModelScope)
    }

    override fun saveLocation(lat: Double, lon: Double) {

    }

    private suspend fun loadWeather(lat: Double, lon: Double) {
        _weatherModel.update { it?.copy(isLoading = true) }

            try {
                Log.d("weather", "HomeViewModel init")
                val weather = getWeatherUseCase(lat, lon)
                _weatherModel.update {
                    it?.copy(
                        isLoading = false,
                        date = weather.date,
                        lat = weather.lat,
                        lon = weather.lon,
                        main = weather.main,
                        errorMessage = null
                    )
                }
            } catch (e: Exception) {
                _weatherModel.update {
                    it?.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Unknown error"
                    )
                }
            }
    }

    override fun saveBirthDate(year: String, month: String, day: String) {
        viewModelScope.launch {
            saveBirthDateUseCase(year, month, day)
        }
    }

    fun loadBirthDate() {
        viewModelScope.launch {
            _birthDateState.update { it.copy(isLoading = true) }

            try {
                val birthDateString: String? = getBirthDateUseCase()
                Log.e("확인", "birthDate: $birthDateString")
                if (!birthDateString.isNullOrBlank()) {
                    val parts = birthDateString.split("-")
                    if (parts.size == 3) {
                        _birthDateState.update {
                            it.copy(
                                year = parts[0],
                                month = parts[1],
                                day = parts[2],
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    } else {
                        _birthDateState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "잘못된 생년월일 형식입니다."
                            )
                        }
                    }
                } else {
                    _birthDateState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "저장된 생년월일이 없습니다."
                        )
                    }
                }
            } catch (e: Exception) {
                _birthDateState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "불러오기 오류"
                    )
                }
            }
        }
    }


}