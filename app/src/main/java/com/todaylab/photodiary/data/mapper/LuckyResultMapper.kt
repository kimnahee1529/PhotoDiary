package com.todaylab.photodiary.data.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.LuckyResultEntity
import com.todaylab.photodiary.domain.model.LuckyResult

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