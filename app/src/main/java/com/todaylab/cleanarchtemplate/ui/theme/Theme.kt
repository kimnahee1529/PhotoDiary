package com.todaylab.cleanarchtemplate.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import kr.co.ui.theme.ColorSet
import kr.co.ui.theme.LuckyColor

private val LocalColors = staticCompositionLocalOf { ColorSet.Lucky.lightColors }

@Composable
fun LuckyTheme(
    colorSet: ColorSet = ColorSet.Lucky,
    darkTheme: Boolean = isSystemInDarkTheme(),
    shapes: Shapes = Shapes,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        colorSet.lightColors
    } else {
        colorSet.lightColors
    }

    CompositionLocalProvider(LocalColors provides colors) {
        CompositionLocalProvider(LocalTypography provides Typography) {
            MaterialTheme(
                content = content,
                shapes = shapes,
            )
        }
    }
}

val MaterialTheme.colors: LuckyColor
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

val MaterialTheme.typo: LuckyTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
