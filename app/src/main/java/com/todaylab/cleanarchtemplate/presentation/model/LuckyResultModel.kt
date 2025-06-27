package com.todaylab.cleanarchtemplate.presentation.model

import com.todaylab.cleanarchtemplate.domain.model.Color
import java.time.LocalDate

data class LuckyResultModel(
    val id: String,
    val date: LocalDate,
    val animal: String,
    val numbers: Int,
    val initials: List<Char>,
    val color: Color,
)