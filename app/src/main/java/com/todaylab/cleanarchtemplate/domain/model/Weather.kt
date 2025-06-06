package com.todaylab.cleanarchtemplate.domain.model

import java.util.Date

/**
 * domain layer
 * weather model
 */
data class Weather(
    val lang: Double,
    val lat: Double,
    val date: Date = Date(),
    val summary: String? = null,
    val main: String,
    val description: String,
    val maxTemp: Double,
    val minTemp: Double
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Weather) return false
        // Weather is equal if date & location is equal
        return (this.lang == other.lang
                && this.lat == other.lat
                && this.date == other.date)
    }
}
