package com.todaylab.photodiary.domain.model

import java.util.Date

/**
 * domain weather model
 */
data class Weather(
    val date: Date,
    val lat: Double,
    val lon: Double,
    val main: String,
    val description: String,
    val icon: String
)