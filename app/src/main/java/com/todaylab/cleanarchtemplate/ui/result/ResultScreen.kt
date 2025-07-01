package com.todaylab.cleanarchtemplate.ui.result

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.presentation.result.ResultViewModel
import com.todaylab.cleanarchtemplate.ui.mapper.LuckyResultMapper
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResultRoute(
    viewModel: ResultViewModel = hiltViewModel()
) {
    val resultModel by viewModel.screenModel.collectAsState()
    val resultState = remember(resultModel) {
        resultModel.mapData(LuckyResultMapper::mapToLow)
    }

    LaunchedEffect(Unit) {
        Timber.d("luckyResult: ${resultState}")
    }

    ResultScreen(
        luckyResult = resultState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    luckyResult: DataResource<LuckyResultState>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("오늘 행운의 운세는? ☘") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("$luckyResult")
        }
    }
}
