package com.todaylab.cleanarchtemplate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.todaylab.cleanarchtemplate.ui.home.HomeRoute
import com.todaylab.cleanarchtemplate.ui.theme.CleanarchtemplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CleanarchtemplateTheme {
                HomeRoute()
            }
        }
    }
}
