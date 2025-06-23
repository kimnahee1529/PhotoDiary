package com.todaylab.cleanarchtemplate.presentation.model

data class BirthDateModel(
    val year: String = "",
    val month: String = "",
    val day: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)