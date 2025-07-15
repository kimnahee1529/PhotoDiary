package com.todaylab.cleanarchtemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.todaylab.cleanarchtemplate.presentation.navigation.AppNavGraph
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LuckyTheme {
                val navController = rememberNavController()
                AppNavGraph(navController = navController)
            }
        }
    }
}