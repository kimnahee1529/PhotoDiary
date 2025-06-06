package com.todaylab.cleanarchtemplate.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen
import com.todaylab.cleanarchtemplate.presentation.main.MainScreen
import com.todaylab.cleanarchtemplate.presentation.main.ScreenA
import com.todaylab.cleanarchtemplate.presentation.main.ScreenB

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(AppRoute.Main.route) {
        MainScreen(navController)
    }
    composable(AppRoute.ScreenA.route) {
        ScreenA(navController)
    }
    composable(AppRoute.ScreenB.route) {
        ScreenB(navController)
    }
    composable(AppRoute.Fortune.route) {
        FortuneScreen(navController)
    }
}