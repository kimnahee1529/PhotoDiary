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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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
        viewModelScope.launch {
            val todayId = LocalDate.now().toYyyyMMdd()
            val result = getLuckyResultUseCase(todayId)
            when (val result = getLuckyResultUseCase(todayId)) {
                is DataResource.Success -> {
                    _resultModel.value = DataResource.success(result.data.toPresentation())
                }

                is DataResource.Loading -> {
                    _resultModel.value = DataResource.loading()
                }

                is DataResource.Error -> {
                    _resultModel.value = DataResource.error(result.throwable)
                }
            }
            Timber.d("랜덤 결과: ${result}")
        }
    }
}