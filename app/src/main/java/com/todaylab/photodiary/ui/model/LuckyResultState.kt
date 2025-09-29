package com.todaylab.photodiary.ui.model

import com.todaylab.photodiary.domain.model.Color
import java.time.LocalDate

data class LuckyResultState(
    val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
)

