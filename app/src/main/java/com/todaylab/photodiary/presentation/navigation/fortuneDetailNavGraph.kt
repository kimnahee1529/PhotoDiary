package com.todaylab.photodiary.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

fun NavGraphBuilder.fortuneDetailNavGraph(navController: NavHostController) {
    composable(AppRoute.Fortune1.route) {
//        FortuneScreen1(navController)
    }
    composable(AppRoute.Fortune2.route) {
//        FortuneScreen2(navController)
    }
    composable(AppRoute.Fortune3.route) {
//        FortuneScreen3(navController)
    }
    composable(AppRoute.Fortune4.route) {
//        FortuneScreen4(navController)
    }
}
