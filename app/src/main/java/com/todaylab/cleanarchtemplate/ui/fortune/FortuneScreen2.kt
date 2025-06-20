package com.todaylab.cleanarchtemplate.presentation.fortune

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.todaylab.cleanarchtemplate.presentation.navigation.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FortuneScreen2(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("운세 상세 2") },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("FortuneScreen2")
//            Button(onClick = { navController.navigate(AppRoute.Fortune1.route) }) {
//                Text("운세 상세 1")
//            }
//            Button(onClick = { navController.navigate(AppRoute.Fortune2.route) }) {
//                Text("운세 상세 2")
//            }
//            Button(onClick = { navController.navigate(AppRoute.Fortune3.route) }) {
//                Text("운세 상세 3")
//            }
//            Button(onClick = { navController.navigate(AppRoute.Fortune4.route) }) {
//                Text("운세 상세 4")
//            }
        }
    }
}
