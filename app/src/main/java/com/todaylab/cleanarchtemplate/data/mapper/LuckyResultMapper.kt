package com.todaylab.cleanarchtemplate.data.mapper

import com.todaylab.cleanarchtemplate.core.base.BaseModelMapper
import com.todaylab.cleanarchtemplate.data.model.LuckyResultEntity
import com.todaylab.cleanarchtemplate.domain.model.LuckyResult

/**
 * Lucky result mapper
 * - high level model: LuckyResult (domain layer)
 * - low level model: LuckyResultEntity (data layer)
 */
internal object LuckyResultMapper : BaseModelMapper<LuckyResult, LuckyResultEntity> {
    override fun mapToHigh(low: LuckyResultEntity): LuckyResult {
        return LuckyResult(
            id = low.id,
            date = low.date,
            animal = low.animal,
            numbers = low.numbers,
            initials = low.initials,
            color = low.color,
        )
    }

    override fun mapToLow(high: LuckyResult): LuckyResultEntity {
        return LuckyResultEntity(
            id = high.id,
            date = high.date,
            animal = high.animal,
            numbers = high.numbers,
            initials = high.initials,
            color = high.color,
        )
    }
}