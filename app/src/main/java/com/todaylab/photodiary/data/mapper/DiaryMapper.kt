package com.todaylab.photodiary.data.mapper

import com.todaylab.photodiary.core.BaseModelMapper
import com.todaylab.photodiary.data.model.BirthDateEntity
import com.todaylab.photodiary.data.model.DiaryEntity
import com.todaylab.photodiary.domain.model.BirthDate
import com.todaylab.photodiary.domain.model.Diary
import com.todaylab.photodiary.local.model.DiaryLocal
import java.time.LocalDate

/**
 * Diary mapper
 * - high level model: Diary (domain layer)
 * - low level model: DiaryEntity (data layer)
 */

// Data → Domain
fun DiaryEntity.toDomain(): Diary = Diary(
    id = id,
    title = title,
    date = date,
    weather = weather,
    wakeTime = wakeTime,
    sleepTime = sleepTime,
    content = content,
    photoUris = photoUris
)

// Domain → Data
fun Diary.toData(): DiaryEntity = DiaryEntity(
    id = id,
    title = title,
    date = date,
    weather = weather,
    wakeTime = wakeTime,
    sleepTime = sleepTime,
    content = content,
    photoUris = photoUris
)

internal object DiaryMapper : BaseModelMapper<Diary, DiaryEntity> {
    override fun mapToHigh(low: DiaryEntity): Diary {
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

    override fun mapToLow(high: Diary): DiaryEntity {
        return DiaryEntity(
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