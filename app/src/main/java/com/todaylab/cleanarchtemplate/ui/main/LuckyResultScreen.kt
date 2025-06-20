package com.todaylab.cleanarchtemplate.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun LuckyResultRoute(
    navController: NavHostController
){
    LuckyResultScreen()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LuckyResultScreen(

) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("오늘 행운의 숫자는? ☘") },
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
            Text("LuckyResultScreen")
        }
    }
}
