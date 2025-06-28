package com.todaylab.cleanarchtemplate.ui.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState

/**
 * Lucky result mapper
 * - high level model: LuckyResultModel (presentation layer)
 * - low level model: LuckyResultState (ui layer)
 */
internal object LuckyResultMapper : BaseModelMapper<LuckyResultModel, LuckyResultState> {
    override fun mapToHigh(low: LuckyResultState): LuckyResultModel {
        return LuckyResultModel(
            id = low.id,
            date = low.date,
            animal = low.animal,
            numbers = low.numbers,
            initials = low.initials,
            color = low.color
        )
    }

    override fun mapToLow(high: LuckyResultModel): LuckyResultState {
        return LuckyResultState(
            id = high.id,
            date = high.date,
            animal = high.animal,
            numbers = high.numbers,
            initials = high.initials,
            color = high.color
        )
    }
}