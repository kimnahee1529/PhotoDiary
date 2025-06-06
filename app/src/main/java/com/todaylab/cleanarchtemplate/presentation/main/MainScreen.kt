package com.todaylab.cleanarchtemplate.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.todaylab.cleanarchtemplate.presentation.navigation.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("메인 화면") }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // TopAppBar와 겹치지 않도록 처리
                .padding(16.dp),       // 좌우 여백 추가
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { navController.navigate(AppRoute.ScreenA.route) }) {
                Text("Screen A")
            }
            Button(onClick = { navController.navigate(AppRoute.ScreenB.route) }) {
                Text("Screen B")
            }
            Button(onClick = { navController.navigate(AppRoute.Fortune.route) }) {
                Text("Fortune")
            }
        }
    }
}
