package com.todaylab.cleanarchtemplate.presentation.result

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.core.toYyyyMMdd
import com.todaylab.cleanarchtemplate.domain.usecase.GetBirthDateUseCase
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultByIdUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import com.todaylab.cleanarchtemplate.presentation.mapper.BirthDateMapper
import com.todaylab.cleanarchtemplate.presentation.mapper.LuckyResultMapper
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate
import javax.inject.Inject

interface ResultEvent {
    fun getLuckyResult()
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLuckyResultById: GetLuckyResultByIdUseCase,
    private val getBirthDate: GetBirthDateUseCase,
) : BaseViewModel<DataResource<LuckyResultModel>>(savedStateHandle), ResultEvent {

    private val _luckyResult: MutableStateFlow<DataResource<LuckyResultModel>> =
        MutableStateFlow(DataResource.loading())
    private val _luckyResultbirthDate =
        MutableStateFlow<DataResource<BirthDateModel>>(DataResource.loading())
    override val screenModel = _luckyResult.asStateFlow()

    init {
        getLuckyResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLuckyResult() {
        val id = LocalDate.now().toYyyyMMdd()

        viewModelScope.launch(Dispatchers.IO) {
            val birthDateResource: DataResource<BirthDateModel> = loadBirthDate()
            val year = birthDateResource.getDataOrNull()?.year
            val month = birthDateResource.getDataOrNull()?.month
            val day = birthDateResource.getDataOrNull()?.day
            Timber.d("생년월일 id - $year + $month + $day + $id")

            _luckyResult.update {
                getLuckyResultById("$year$month$day$id").mapData(LuckyResultMapper::mapToLow)
            }
            Timber.d("생년월일 year - ${_luckyResultbirthDate.value.getDataOrNull()?.year}")
            Timber.d("생년월일 month - ${_luckyResultbirthDate.value.getDataOrNull()?.month}")
            Timber.d("생년월일 day - ${_luckyResultbirthDate.value.getDataOrNull()?.day}")
        }
    }

    private suspend fun loadBirthDate(): DataResource<BirthDateModel> {
        return withContext(Dispatchers.IO) {
            val result = getBirthDate().mapData(BirthDateMapper::mapToLow)
//            _luckyResultbirthDate.update { result }
            Timber.d("생년월일1 (loadBirthDate 내부) - ${result.getDataOrNull()}")
            result // 결과 반환
        }
    }
}