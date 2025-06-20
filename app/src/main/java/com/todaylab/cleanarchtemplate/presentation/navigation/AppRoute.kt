package com.todaylab.cleanarchtemplate.presentation.navigation

sealed class AppRoute(val route: String) {
    object Main : AppRoute("main")
    object LuckyResult : AppRoute("LuckyResult")
    object ScreenB : AppRoute("screen_b")
    object Fortune : AppRoute("fortune")

    // Fortune 하위 화면
    object Fortune1 : AppRoute("fortune/detail1")
    object Fortune2 : AppRoute("fortune/detail2")
    object Fortune3 : AppRoute("fortune/detail3")
    object Fortune4 : AppRoute("fortune/detail4")
}