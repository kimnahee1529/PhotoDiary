package com.todaylab.photodiary.ui.util

import java.time.*

fun LocalDate.toEpochMillisUtc(): Long =
    this.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()

fun Long.toLocalDateUtc(): LocalDate =
    Instant.ofEpochMilli(this).atZone(ZoneOffset.UTC).toLocalDate()

