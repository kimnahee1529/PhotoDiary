package com.todaylab.photodiary.presentation.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.DiaryEntity
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.presentation.model.DiaryModel

/**
 * Diary mapper
 * - high level model: Diary (domain layer)
 * - low level model: DiaryModel (presentation layer)
 */
internal object DiaryMapper : BaseModelMapper<Diary, DiaryModel> {
    override fun mapToHigh(low: DiaryModel): Diary {
        return Diary(
            id = low.id,
            title = low.title,
            date = low.date,
            weather = low.weather,
            wakeTime = low.wakeTime,
            sleepTime = low.sleepTime,
            content = low.content,
            photoUris = low.photoUris
        )
    }

    override fun mapToLow(high: Diary): DiaryModel {
        return DiaryModel(
            id = high.id,
            title = high.title,
            date = high.date,
            weather = high.weather,
            wakeTime = high.wakeTime,
            sleepTime = high.sleepTime,
            content = high.content,
            photoUris = high.photoUris
        )
    }
}
