package com.todaylab.cleanarchtemplate.data

import com.todaylab.cleanarchtemplate.data.model.BirthDateEntity
import com.todaylab.cleanarchtemplate.domain.model.BirthDate

/**
 * data layer
 * model mapper extension functions
 */
internal fun BirthDateEntity.toDomain(): BirthDate {
    return BirthDate(
        year = this.year,
        month = this.month,
        day = this.day,
    )
}

internal fun BirthDate.toData(): BirthDateEntity {
    return BirthDateEntity(
        year = this.year,
        month = this.month,
        day = this.day,
    )
}
