package com.todaylab.cleanarchtemplate.presentation.mapper

import com.todaylab.cleanarchtemplate.core.BaseModelMapper
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult
import com.todaylab.cleanarchtemplate.presentation.model.LuckyResultModel

/**
 * Lucky result mapper
 * - high level model: LuckyResult (domain layer)
 * - low level model: LuckyResultModel (presentation layer)
 */
internal object LuckyResultMapper : BaseModelMapper<LuckyResult, LuckyResultModel> {
    override fun mapToHigh(low: LuckyResultModel): LuckyResult {
        return LuckyResult(
            id = low.id,
            date = low.date,
            animal = low.animal,
            numbers = low.numbers,
            initials = low.initials,
            color = low.color
        )
    }

    override fun mapToLow(high: LuckyResult): LuckyResultModel {
        return LuckyResultModel(
            id = high.id,
            date = high.date,
            animal = high.animal,
            numbers = high.numbers,
            initials = high.initials,
            color = high.color
        )
    }
}
