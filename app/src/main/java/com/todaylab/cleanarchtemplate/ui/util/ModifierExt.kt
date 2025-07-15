package com.todaylab.cleanarchtemplate.ui.util

import androidx.compose.foundation.layout.offset
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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