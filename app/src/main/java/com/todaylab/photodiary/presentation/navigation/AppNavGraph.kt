package com.todaylab.photodiary.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppRoute.Main.route
    ) {
        mainNavGraph(navController)
        fortuneDetailNavGraph(navController)
    }
}
