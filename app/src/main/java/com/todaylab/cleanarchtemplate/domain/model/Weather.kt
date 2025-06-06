package com.todaylab.cleanarchtemplate.domain.model

import java.util.Date

/**
 * domain layer
 * weather model
 */
data class Weather(
    val lang: Long,
    val lat: Long,
    val date: Date,
    val summary: String,
    val main: String,
    val description: String,
    val maxTemp: Long,
    val minTemp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (other !is Weather) return false
        // Weather is equal if date & location is equal
        return (this.lang == other.lang
                && this.lat == other.lat
                && this.date == other.date)
    }
}
