package com.todaylab.photodiary.presentation.model

import com.todaylab.photodiary.domain.model.Solution

data class SolutionModel(
    val id: Int,
    val text: String
)

fun Solution.toPresentation(): SolutionModel =
    SolutionModel(id, text)
