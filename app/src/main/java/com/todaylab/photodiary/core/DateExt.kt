package com.todaylab.photodiary.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun LocalDate.toYyyyMMdd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.format(formatter)
}

fun LocalDate.createDateBasedId(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.format(formatter)
}