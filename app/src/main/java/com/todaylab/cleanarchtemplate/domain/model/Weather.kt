package com.todaylab.cleanarchtemplate.domain.model

import java.util.Date

/**
 * domain layer
 * weather model
 */
data class Weather(
    val long: Double,
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
        return (this.long == other.long
                && this.lat == other.lat
                && this.date == other.date)
    }
}
