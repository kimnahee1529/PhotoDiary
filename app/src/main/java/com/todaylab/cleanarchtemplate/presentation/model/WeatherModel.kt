package com.todaylab.cleanarchtemplate.presentation.model

data class WeatherModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)