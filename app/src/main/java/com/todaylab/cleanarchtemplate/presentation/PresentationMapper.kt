package com.todaylab.cleanarchtemplate.presentation

import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel

internal fun BirthDate.toPresentation() = BirthDateModel(
    year = year,
    month = month,
    day = day,
)

internal fun BirthDateModel.toDomain() = BirthDate(
    year = year,
    month = month,
    day = day,
)