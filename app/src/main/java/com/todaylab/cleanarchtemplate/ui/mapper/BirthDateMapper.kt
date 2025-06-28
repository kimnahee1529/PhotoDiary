package com.todaylab.cleanarchtemplate.ui.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.presentation.model.BirthDateModel
import com.todaylab.cleanarchtemplate.ui.model.BirthDateState

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