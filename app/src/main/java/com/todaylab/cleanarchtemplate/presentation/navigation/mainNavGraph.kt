package com.todaylab.cleanarchtemplate.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen
import com.todaylab.cleanarchtemplate.ui.home.HomeRoute
import com.todaylab.cleanarchtemplate.ui.result.ResultRoute
import com.todaylab.cleanarchtemplate.ui.screenB.ScreenB

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(AppRoute.Main.route) {
        HomeRoute(navController)
    }
    composable(AppRoute.LuckyResult.route) {
        ResultRoute(navController)
    }
    composable(AppRoute.ScreenB.route) {
        ScreenB(navController)
    }
    composable(AppRoute.Fortune.route) {
        FortuneScreen(navController)
    }
}