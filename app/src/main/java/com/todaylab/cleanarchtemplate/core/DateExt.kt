package com.todaylab.cleanarchtemplate.core

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toYyyyMMdd(): String {
    val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
    return this.format(formatter)
}