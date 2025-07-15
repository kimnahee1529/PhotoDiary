package com.todaylab.cleanarchtemplate.ui.result

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.core.DataResource
import com.todaylab.cleanarchtemplate.presentation.result.ResultViewModel
import com.todaylab.cleanarchtemplate.ui.mapper.LuckyResultMapper
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState
import com.todaylab.cleanarchtemplate.ui.theme.LocalTypography
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.theme.typo
import com.todaylab.cleanarchtemplate.ui.widget.LuckyResultPropertiesDisplay
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
        Timber.d("luckyResult: ${resultState.getDataOrNull()?.animal}")
    }

    ResultScreen(
        luckyResult = resultState,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResultScreen(
    luckyResult: DataResource<LuckyResultState>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        modifier = Modifier.padding(top = 10.dp),
                        text = stringResource(R.string.lucky_result_question),
                        fontFamily = FontFamily(Font(R.font.backdahyeon_font)),
                        fontSize = 45.dp.value.sp,
                        color = MaterialTheme.colors.text1
                    )
                },
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

            if (luckyResult is DataResource.Success) {
                LuckyResultPropertiesDisplay(
                    luckyResult = LuckyResultState(
                        luckyResult.getDataOrNull()!!.id,
                        luckyResult.getDataOrNull()!!.date,
                        luckyResult.getDataOrNull()!!.animal,
                        luckyResult.getDataOrNull()!!.numbers,
                        luckyResult.getDataOrNull()!!.initials,
                        luckyResult.getDataOrNull()!!.color
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    LuckyTheme {
        Text(
            text = "오늘 행운의 운세는? ☘",
            style = MaterialTheme.typo.cardTitle
        )
    }
}