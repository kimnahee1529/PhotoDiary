package com.todaylab.photodiary.ui.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.presentation.model.BirthDateModel
import com.todaylab.photodiary.ui.model.BirthDateState

/**
 * Birth date mapper
 * - high level model: BirthDateModel (presentation layer)
 * - low level model: BirthDateState (ui layer)
 */
internal object BirthDateMapper : BaseModelMapper<BirthDateModel, BirthDateState> {
    override fun mapToHigh(low: BirthDateState): BirthDateModel {
        return BirthDateModel(
            year = low.year,
            month = low.month,
            day = low.day,
        )
    }

    override fun mapToLow(high: BirthDateModel): BirthDateState {
        return BirthDateState(
            year = high.year,
            month = high.month,
            day = high.day,
        )
    }
}