package com.todaylab.photodiary.ui.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
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

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier {
    return this.then(
        Modifier.clickable(
            indication = null, // 👈 Ripple 제거
            interactionSource = remember { MutableInteractionSource() },
            onClick = onClick
        )
    )
}

fun Modifier.wobbleClickable(
    angle: Float = 12f,
    duration: Int = 80,
    enabled: Boolean = true,
    onClick: () -> Unit = {}
): Modifier = composed {
    val rotation = remember { Animatable(0f) }
    var trigger by remember { mutableStateOf(0) }

    // 애니메이션 시퀀스
    LaunchedEffect(trigger) {
        if (trigger == 0) return@LaunchedEffect
        rotation.animateTo(+angle, tween(duration))
        rotation.animateTo(-angle, tween(duration))
        rotation.animateTo(+angle / 2f, tween(duration))
        rotation.animateTo(0f, tween(duration))
    }

    this
        .graphicsLayer { rotationZ = rotation.value }
        .noRippleClickable {
            if (enabled && !rotation.isRunning) { // 👈 enabled 체크
                trigger++
                onClick()
            }
        }
}

fun Modifier.wobbleOnAppear(
    angle: Float = 12f,
    duration: Int = 80,
    appearKey: Any // 이 키가 바뀔 때마다 시퀀스 실행
): Modifier = composed {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(appearKey) {
        rotation.snapTo(0f)
        rotation.animateTo(+angle, tween(duration))
        rotation.animateTo(-angle, tween(duration))
        rotation.animateTo(+angle / 2f, tween(duration))
        rotation.animateTo(0f, tween(duration))
    }

    this.graphicsLayer { rotationZ = rotation.value }
}
