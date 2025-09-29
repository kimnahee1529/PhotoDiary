package com.todaylab.photodiary.data.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.BirthDateEntity
import com.todaylab.photodiary.domain.model.BirthDate

/**
 * Birth date mapper
 * - high level model: BirthDate (domain layer)
 * - low level model: BirthDateEntity (data layer)
 */
internal object BirthDateMapper : BaseModelMapper<BirthDate, BirthDateEntity> {
    override fun mapToHigh(low: BirthDateEntity): BirthDate {
        return BirthDate(
            year = low.year,
            month = low.month,
            day = low.day,
        )
    }

    override fun mapToLow(high: BirthDate): BirthDateEntity {
        return BirthDateEntity(
            year = high.year,
            month = high.month,
            day = high.day,
        )
    }

}