package com.todaylab.photodiary.presentation.navigation

sealed class AppRoute(val route: String) {
    object Main : AppRoute("main")
    object DiaryList : AppRoute("diary_list")
    object MagicBook : AppRoute("magic_book")
    object Plant : AppRoute("plant")

    // DiaryList 하위 화면
    object DiaryWrite : AppRoute("diary_write")

    object Fortune1 : AppRoute("fortune/detail1")
    object Fortune2 : AppRoute("fortune/detail2")
    object Fortune3 : AppRoute("fortune/detail3")
    object Fortune4 : AppRoute("fortune/detail4")
}