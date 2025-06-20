package com.todaylab.cleanarchtemplate.presentation.home

data class BirthdayUiState(
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)