package com.todaylab.cleanarchtemplate.ui.model

import com.todaylab.cleanarchtemplate.domain.model.Color
import java.time.LocalDate

data class LuckyResultState(
    val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
)

