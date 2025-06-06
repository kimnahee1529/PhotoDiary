package com.todaylab.cleanarchtemplate.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen1
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen2
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen3
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen4

fun NavGraphBuilder.fortuneDetailNavGraph(navController: NavHostController) {
    composable(AppRoute.Fortune1.route) {
        FortuneScreen1(navController)
    }
    composable(AppRoute.Fortune2.route) {
        FortuneScreen2(navController)
    }
    composable(AppRoute.Fortune3.route) {
        FortuneScreen3(navController)
    }
    composable(AppRoute.Fortune4.route) {
        FortuneScreen4(navController)
    }
}
