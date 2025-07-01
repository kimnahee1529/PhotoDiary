package com.todaylab.cleanarchtemplate.presentation.result

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.core.toYyyyMMdd
import com.todaylab.cleanarchtemplate.domain.usecase.GetLuckyResultByIdUseCase
import com.todaylab.cleanarchtemplate.presentation.BaseViewModel
import com.todaylab.cleanarchtemplate.presentation.mapper.LuckyResultMapper
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel
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
    private val getLuckyResultById: GetLuckyResultByIdUseCase,
) : BaseViewModel<DataResource<LuckyResultModel>>(savedStateHandle), ResultEvent {

    private val _luckyResult: MutableStateFlow<DataResource<LuckyResultModel>> =
        MutableStateFlow(DataResource.loading())
    override val screenModel = _luckyResult.asStateFlow()

    init {
        getLuckyResult()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getLuckyResult() {
        val id = LocalDate.now().toYyyyMMdd()

        viewModelScope.launch(Dispatchers.IO) {
            _luckyResult.update {
                getLuckyResultById(id).mapData(LuckyResultMapper::mapToLow)
            }
        }
    }
}