package com.todaylab.photodiary.local.model

import com.todaylab.photodiary.data.model.SolutionEntity
import com.todaylab.photodiary.local.LocalMapper

@kotlinx.serialization.Serializable
data class SolutionLocal(
    val id: Int,
    val text: String
): LocalMapper<SolutionEntity> {
    override fun toData(): SolutionEntity =
        SolutionEntity(
            id,
            text
        )
}

fun SolutionEntity.toLocal(): SolutionLocal =
    SolutionLocal(
        id,
        text
    )