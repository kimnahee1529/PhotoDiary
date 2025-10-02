package com.todaylab.photodiary.data.model

import com.todaylab.photodiary.data.DataMapper
import com.todaylab.photodiary.domain.model.Solution

data class SolutionEntity(
    val id: Int,
    val text: String
) : DataMapper<Solution> {
    override fun toDomain(): Solution =
        Solution(
            id,
            text
        )
}
