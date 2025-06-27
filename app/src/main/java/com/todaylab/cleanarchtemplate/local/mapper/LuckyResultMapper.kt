package com.todaylab.cleanarchtemplate.local.mapper

import com.todaylab.cleanarchtemplate.core.base.BaseModelMapper
import com.todaylab.cleanarchtemplate.data.model.LuckyResultEntity
import com.todaylab.cleanarchtemplate.local.model.LuckyResultLocal

/**
 * Lucky result mapper
 * - high level model: LuckyResultEntity (data layer)
 * - low level model: LuckyResultLocal (local layer)
 */
internal object LuckyResultMapper : BaseModelMapper<LuckyResultEntity, LuckyResultLocal> {
    override fun mapToHigh(low: LuckyResultLocal): LuckyResultEntity {
        return LuckyResultEntity(
            id = low.id,
            date = low.date,
            animal = low.animal,
            numbers = low.numbers,
            initials = low.initials,
            color = low.color,
        )
    }

    override fun mapToLow(high: LuckyResultEntity): LuckyResultLocal {
        return LuckyResultLocal(
            id = high.id,
            date = high.date,
            animal = high.animal,
            numbers = high.numbers,
            initials = high.initials,
            color = high.color,
        )
    }

}