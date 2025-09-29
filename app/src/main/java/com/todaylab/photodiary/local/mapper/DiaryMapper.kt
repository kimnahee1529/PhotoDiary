package com.todaylab.photodiary.local.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.BirthDateEntity
import com.todaylab.photodiary.data.model.DiaryEntity
import com.todaylab.photodiary.domain.model.BirthDate
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.local.model.DiaryLocal

/**
 * Diary mapper
 * - high level model: DiaryEntity (data layer)
 * - low level model: DiaryLocal (local layer)
 */

// Local → Data
fun DiaryLocal.toData(): DiaryEntity = DiaryEntity(
    id = id,
    title = title,
    date = date,
    weather = weather,
    wakeTime = wakeTime,
    sleepTime = sleepTime,
    content = content,
    photoUris = photoUris
)

// Data → Local
fun DiaryEntity.toLocal(): DiaryLocal = DiaryLocal(
    id = id,
    title = title,
    date = date,
    weather = weather,
    wakeTime = wakeTime,
    sleepTime = sleepTime,
    content = content,
    photoUris = photoUris
)

internal object DiaryMapper : BaseModelMapper<DiaryEntity, DiaryLocal> {
    override fun mapToHigh(low: DiaryLocal): DiaryEntity {
        return DiaryEntity(
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

    override fun mapToLow(high: DiaryEntity): DiaryLocal {
        return DiaryLocal(
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
