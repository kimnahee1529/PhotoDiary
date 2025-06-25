package com.todaylab.cleanarchtemplate.ui.model

data class BirthDateState(
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)