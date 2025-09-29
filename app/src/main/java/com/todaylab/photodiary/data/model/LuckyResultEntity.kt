package com.todaylab.photodiary.data.model

import com.todaylab.photodiary.domain.model.Color
import java.time.LocalDate

/**
 * data LuckyResult model
 */
data class LuckyResultEntity(
    val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
)