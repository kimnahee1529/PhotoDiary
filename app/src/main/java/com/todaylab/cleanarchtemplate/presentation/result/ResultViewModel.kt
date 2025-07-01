package com.todaylab.cleanarchtemplate.presentation.result

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.core.toYyyyMMdd
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel
import com.todaylab.cleanarchtemplate.presentation.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

interface ResultEvent {
    fun getLuckyResult()
}

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getLuckyResultUseCase: GetLuckyResultUseCase,
) : BaseViewModel(savedStateHandle), ResultEvent {
    private val _resultModel =
        MutableStateFlow<DataResource<LuckyResultModel>>(DataResource.loading())
    val resultModel = _resultModel.asStateFlow()

    init {
        getLuckyResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLuckyResult() {
        val resultId = LocalDate.now().toYyyyMMdd()

        viewModelScope.launch(Dispatchers.IO) {
            val luckyResult = getLuckyResultUseCase(resultId)
            _resultModel.update {
                luckyResult.mapData { it.toPresentation() }
            }
        }
    }
}