package com.todaylab.cleanarchtemplate.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.todaylab.cleanarchtemplate.ui.theme.CleanarchtemplateTheme

@Composable
fun HomeRoute() {
    // todo: get home screen state from view model
    HomeScreen()
}

@Composable
private fun HomeScreen() {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            Text(
                text = "Home",
            )
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    CleanarchtemplateTheme {
        HomeScreen()
    }
}