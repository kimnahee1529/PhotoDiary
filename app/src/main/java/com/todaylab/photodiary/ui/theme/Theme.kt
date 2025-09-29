package com.todaylab.photodiary.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import kr.co.ui.theme.ColorSet
import kr.co.ui.theme.ExampleColor

private val LocalColors = staticCompositionLocalOf { ColorSet.Lucky.lightColors }

@Composable
fun ExampleTheme(
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


    val defaultTypography = MaterialTheme.typography

    CompositionLocalProvider(LocalColors provides colors) {
        CompositionLocalProvider(LocalTypography provides Typography) {
            MaterialTheme(
                content = content,
                shapes = shapes,
            )
        }
    }
}

val MaterialTheme.colors: ExampleColor
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

val MaterialTheme.typo: ExampleTypography
    @Composable
    @ReadOnlyComposable
    get() = LocalTypography.current
