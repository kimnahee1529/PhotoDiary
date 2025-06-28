package com.todaylab.cleanarchtemplate.presentation.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.domain.model.BirthDate
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel

/**
 * Birth date mapper
 * - high level model: BirthDate (domain layer)
 * - low level model: BirthDateModel (presentation layer)
 */
internal object BirthDateMapper : BaseModelMapper<BirthDate, BirthDateModel> {
    override fun mapToHigh(low: BirthDateModel): BirthDate {
        return BirthDate(
            year = low.year,
            month = low.month,
            day = low.day,
        )
    }

    override fun mapToLow(high: BirthDate): BirthDateModel {
        return BirthDateModel(
            year = high.year,
            month = high.month,
            day = high.day,
        )
    }
}