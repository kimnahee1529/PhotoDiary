package com.todaylab.cleanarchtemplate.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.cleanarchtemplate.presentation.fortune.FortuneScreen
import com.todaylab.cleanarchtemplate.ui.home.HomeRoute
import com.todaylab.cleanarchtemplate.ui.result.ResultRoute
import com.todaylab.cleanarchtemplate.ui.screenB.ScreenB

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(AppRoute.Main.route) {
        HomeRoute(
            navToResult = { navController.navigate(AppRoute.LuckyResult.route) }
        )
    }
    composable(AppRoute.LuckyResult.route) {
        ResultRoute()
    }
    composable(AppRoute.ScreenB.route) {
        ScreenB(navController)
    }
    composable(AppRoute.Fortune.route) {
        FortuneScreen(navController)
    }
}