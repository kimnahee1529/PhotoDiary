package com.todaylab.photodiary.ui.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.todaylab.photodiary.R
import com.todaylab.photodiary.presentation.diary.DiaryViewModel
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.widget.LuckyButton
import com.todaylab.sketchmind.ui.widget.topappbar.CommonTopAppBar

@Composable
fun DiaryHomeRoute(
    viewModel: DiaryViewModel = hiltViewModel(),
    navToDiaryWrite: () -> Unit = {},
    popBackStack: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsState()

    DiaryHomeScreen(
        uiState = uiState,
        navToDiaryWrite = navToDiaryWrite,
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryHomeScreen(
    uiState: DiaryViewModel.DiaryUiState = DiaryViewModel.DiaryUiState(),
    navToDiaryWrite: () -> Unit = {},
    popBackStack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.background(MaterialTheme.colors.white),
        topBar = {
            CommonTopAppBar(
                title = "다이어리 리스트 화면",
                style = MaterialTheme.typo.titleSmall,
                onBackClick = { popBackStack() },
                onActionClick = {},
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colors.white)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                items(uiState.diaryList) { diary ->
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        AsyncImage(
                            model = R.drawable.img_example,
                            contentDescription = "example img",
                            modifier = Modifier.fillMaxWidth(),
                            //                            modifier = Modifier
                            //                                .height(240.dp),
                            contentScale = ContentScale.Crop,

                            )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("${diary.date}")
                        Spacer(modifier = Modifier.height(10.dp))
                        Text("${diary.title}")
                        Spacer(modifier = Modifier.height(20.dp))

                    }
                }
            }

            LuckyButton(
                text = "작성하기",
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                onClick = { navToDiaryWrite() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.white
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryHomeScreenPreview() {
    ExampleTheme {
        DiaryHomeScreen()
    }
}