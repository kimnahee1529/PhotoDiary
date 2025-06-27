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
import androidx.navigation.NavHostController
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.presentation.result.ResultViewModel
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState
import com.todaylab.cleanarchtemplate.ui.toUi
import timber.log.Timber

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ResultRoute(
    navController: NavHostController,
    viewModel: ResultViewModel = hiltViewModel()
) {

    val luckyResultModel by viewModel.resultModel.collectAsState()
    val luckyResultState = remember(luckyResultModel) {
        luckyResultModel.mapData {
            it.toUi()
        }
    }

    LaunchedEffect(Unit) {
        Timber.d("luckyResult: ${luckyResultState}")

    }

    ResultScreen(
        luckyResult = luckyResultState
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
//            LuckyNumber()
//            LuckyAnimal()
            Text("$luckyResult")
        }
    }
}
