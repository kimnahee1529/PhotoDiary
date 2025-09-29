package com.todaylab.photodiary.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.todaylab.photodiary.R

val DahyeonFontFamily = FontFamily(
    Font(R.font.backdahyeon_font, weight = FontWeight.Normal)
)

private val ExampleFontFamily = FontFamily(
    Font(R.font.gowundodum_regular, weight = FontWeight.Black),
    Font(R.font.pretendard_black, weight = FontWeight.Black),
    Font(R.font.pretendard_bold, weight = FontWeight.Bold),
    Font(R.font.pretendard_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.pretendard_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.pretendard_light, weight = FontWeight.Light),
    Font(R.font.pretendard_medium, weight = FontWeight.Medium),
    Font(R.font.pretendard_regular, weight = FontWeight.Normal),
    Font(R.font.pretendard_semibold, weight = FontWeight.SemiBold),
    Font(R.font.pretendard_thin, weight = FontWeight.Thin),
)

// Set of Material typography styles to start with
val Typography = ExampleTypography(
    gowunH1 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 30.sp,
        lineHeight = 30.sp * 1.35,
        letterSpacing = TextUnit.Unspecified
    ),
    graphic = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        lineHeight = 50.sp * 1.2f,
        letterSpacing = TextUnit.Unspecified
    ),
    head1 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        lineHeight = 30.sp * 1.35,
        letterSpacing = TextUnit.Unspecified
    ),
    head2 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.35,
        letterSpacing = TextUnit.Unspecified
    ),
    head3 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    head4 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    body1 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    body2 = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    label = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    button = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 14.sp * 1.6,
        letterSpacing = TextUnit.Unspecified
    ),
    name = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6,
    ),
    weather = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 40.sp * 1.6,
    ),
    pageName = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = TextUnit.Unspecified,
    ),
    mainDate = TextStyle(
        fontFamily = ExampleFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 29.sp,
        letterSpacing = TextUnit.Unspecified
    ),
    displayB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    displaySB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = TextUnit.Unspecified,
        letterSpacing = TextUnit.Unspecified
    ),
    headerM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2B = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2SB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    header2M = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2, // 120%
        letterSpacing = (-0.01).em // -0.01%
    ),
    titleSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.4, // 140%
        letterSpacing = (-0.01).em // -0.01%
    ),
    titleM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        lineHeight = 18.sp * 1.4, // 140%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodySB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    bodyR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelSB = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelM = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelR = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    labelL = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.6, // 160%
        letterSpacing = (-0.01).em // -0.01%
    ),
    titleLarge = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 45.sp,
        lineHeight = 45.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    titleMedium = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 40.sp,
        lineHeight = 40.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    titleSmall = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 35.sp,
        lineHeight = 35.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    labelLarge = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 20.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    labelMedium = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 12.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    labelSmall = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 12.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    nickNameLabel = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 10.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
    badgeSmall = TextStyle(
        fontFamily = DahyeonFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.6,
        letterSpacing = (-0.01).em
    ),
)

@Immutable
data class ExampleTypography(
    val gowunH1: TextStyle = TextStyle.Default,
    val graphic: TextStyle = TextStyle.Default,
    val head1: TextStyle = TextStyle.Default,
    val head2: TextStyle = TextStyle.Default,
    val head3: TextStyle = TextStyle.Default,
    val head4: TextStyle = TextStyle.Default,
    val body1: TextStyle = TextStyle.Default,
    val body2: TextStyle = TextStyle.Default,
    val label: TextStyle = TextStyle.Default,
    val button: TextStyle = TextStyle.Default,
    val name: TextStyle = TextStyle.Default,
    val weather: TextStyle = TextStyle.Default,
    val pageName: TextStyle = TextStyle.Default,
    val mainDate: TextStyle = TextStyle.Default,
    val displayB: TextStyle = TextStyle.Default,
    val displaySB: TextStyle = TextStyle.Default,
    val headerB: TextStyle = TextStyle.Default,
    val headerSB: TextStyle = TextStyle.Default,
    val headerM: TextStyle = TextStyle.Default,
    val header2B: TextStyle = TextStyle.Default,
    val header2SB: TextStyle = TextStyle.Default,
    val header2M: TextStyle = TextStyle.Default,
    val titleSB: TextStyle = TextStyle.Default,
    val titleM: TextStyle = TextStyle.Default,
    val bodyB: TextStyle = TextStyle.Default,
    val bodySB: TextStyle = TextStyle.Default,
    val bodyM: TextStyle = TextStyle.Default,
    val bodyR: TextStyle = TextStyle.Default,
    val labelSB: TextStyle = TextStyle.Default,
    val labelM: TextStyle = TextStyle.Default,
    val labelR: TextStyle = TextStyle.Default,
    val labelL: TextStyle = TextStyle.Default,
    val titleLarge: TextStyle = TextStyle.Default,
    val titleMedium: TextStyle = TextStyle.Default,
    val titleSmall: TextStyle = TextStyle.Default,
    val labelLarge: TextStyle = TextStyle.Default,
    val labelMedium: TextStyle = TextStyle.Default,
    val labelSmall: TextStyle = TextStyle.Default,
    val badgeSmall: TextStyle = TextStyle.Default,
    val nickNameLabel: TextStyle = TextStyle.Default,
    )

val LocalTypography = staticCompositionLocalOf { ExampleTypography() }
