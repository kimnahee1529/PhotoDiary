package com.todaylab.cleanarchtemplate.core

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.domain.model.Color
import kotlin.math.sin

// 아래 위로 흔들리는 애니메이션 확장함수
fun Modifier.floatingAnimationShared(
    time: Float,
    phaseOffset: Float = 0f, // 출발 위치
    amplitude: Dp = 10.dp // 떠오르는 최대 높이
): Modifier {
    val offsetY = sin(time + phaseOffset) * amplitude.value
    return this.offset(y = offsetY.dp)
}

@DrawableRes
fun String.toAnimalImageRes(): Int? = when (this) {
    "강아지" -> R.drawable.animal_dog
    "고양이" -> R.drawable.animal_cat
    "토끼" -> R.drawable.animal_rabbit
    "여우" -> R.drawable.animal_fox
    "판다" -> R.drawable.animal_panda
    "원숭이" -> R.drawable.animal_monkey
    "개구리" -> R.drawable.animal_frog
    else -> R.drawable.animal_dog
}

@DrawableRes
fun Color.toDrawableRes(): Int = when (this) {
    Color.RED -> R.drawable.color_red
    Color.ORANGE -> R.drawable.color_orange
    Color.YELLOW -> R.drawable.color_yellow
    Color.GREEN -> R.drawable.color_green
    Color.BLUE -> R.drawable.color_blue
    Color.PURPLE -> R.drawable.color_purple
    Color.BLACK -> R.drawable.color_black
    Color.WHITE -> R.drawable.color_white
    Color.PINK -> R.drawable.color_pink
    else -> R.drawable.color_red
}

fun String.toColorEnum(): Color? = when (this.uppercase()) {
    "RED" -> Color.RED
    "ORANGE" -> Color.ORANGE
    "YELLOW" -> Color.YELLOW
    "GREEN" -> Color.GREEN
    "BLUE" -> Color.BLUE
    "PURPLE" -> Color.PURPLE
    "BLACK" -> Color.BLACK
    "WHITE" -> Color.WHITE
    "PINK" -> Color.PINK
    else -> null
}