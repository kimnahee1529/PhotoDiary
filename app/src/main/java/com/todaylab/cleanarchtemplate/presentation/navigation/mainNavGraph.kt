package com.todaylab.cleanarchtemplate.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen
import com.todaylab.cleanarchtemplate.ui.main.LuckyResultRoute
import com.todaylab.cleanarchtemplate.ui.main.MainRoute
import com.todaylab.cleanarchtemplate.ui.main.ScreenB

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(AppRoute.Main.route) {
        MainRoute(navController)
    }
    composable(AppRoute.LuckyResult.route) {
        LuckyResultRoute(navController)
    }
    composable(AppRoute.ScreenB.route) {
        ScreenB(navController)
    }
    composable(AppRoute.Fortune.route) {
        FortuneScreen(navController)
    }
}