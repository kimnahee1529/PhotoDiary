package com.todaylab.cleanarchtemplate.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
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


    val defaultTypography = MaterialTheme.typography
    val customTypography = Typography(
        displayLarge = defaultTypography.displayLarge.copy(fontFamily = DahyeonFontFamily),
        displayMedium = defaultTypography.displayMedium.copy(fontFamily = DahyeonFontFamily),
        displaySmall = defaultTypography.displaySmall.copy(fontFamily = DahyeonFontFamily),
        headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = DahyeonFontFamily),
        headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = DahyeonFontFamily),
        headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = DahyeonFontFamily),
        titleLarge = defaultTypography.titleLarge.copy(fontFamily = DahyeonFontFamily),
        titleMedium = defaultTypography.titleMedium.copy(fontFamily = DahyeonFontFamily),
        titleSmall = defaultTypography.titleSmall.copy(fontFamily = DahyeonFontFamily),
        bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = DahyeonFontFamily),
        bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = DahyeonFontFamily),
        bodySmall = defaultTypography.bodySmall.copy(fontFamily = DahyeonFontFamily),
        labelLarge = defaultTypography.labelLarge.copy(fontFamily = DahyeonFontFamily),
        labelMedium = defaultTypography.labelMedium.copy(fontFamily = DahyeonFontFamily),
        labelSmall = defaultTypography.labelSmall.copy(fontFamily = DahyeonFontFamily),
    )

    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            content = content,
            typography = customTypography,
            shapes = shapes,
        )
    }
}

val MaterialTheme.colors: LuckyColor
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current