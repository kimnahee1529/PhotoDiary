package com.todaylab.photodiary.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.todaylab.photodiary.ui.diary.DiaryEditRoute
import com.todaylab.photodiary.ui.diary.DiaryHomeRoute
import com.todaylab.photodiary.ui.home.HomeRoute
import com.todaylab.photodiary.ui.magic.MagicBookRoute
import com.todaylab.photodiary.ui.plant.PlantRoute

fun NavGraphBuilder.mainNavGraph(navController: NavHostController) {
    composable(AppRoute.Main.route) {
        HomeRoute(
            navToDiaryList = { navController.navigate(AppRoute.DiaryList.route) },
            navToMagicBook = { navController.navigate(AppRoute.MagicBook.route) },
            navToPlant = { navController.navigate(AppRoute.Plant.route) },
        )
    }
    composable(AppRoute.DiaryList.route) {
        DiaryHomeRoute(
            popBackStack = { navController.popBackStack() },
            navToDiaryWrite = { navController.navigate(AppRoute.DiaryWrite.route) },

            )
    }
    composable(AppRoute.MagicBook.route) {
        MagicBookRoute(
            popBackStack = { navController.popBackStack() }
        )
    }
    composable(AppRoute.Plant.route) {
        PlantRoute(
            popBackStack = { navController.popBackStack() }
        )
    }
    composable(AppRoute.DiaryWrite.route) {
        DiaryEditRoute(
            popBackStack = { navController.popBackStack() }
        )
    }
}