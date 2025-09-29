package com.todaylab.photodiary.ui.widget.interactionItem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.R
import com.todaylab.photodiary.domain.model.WeatherType
import com.todaylab.photodiary.ui.model.WeatherState
import com.todaylab.photodiary.ui.util.noRippleClickable

@Composable
fun ToggleWindowImage(
    modifier: Modifier = Modifier,
    isLightOn: Boolean = false,
    windowPosX: Dp,
    windowPosY: Dp,
    isLocationGranted: Boolean,
    forceOpen: Boolean = false,
    weather: WeatherType = WeatherType.ETC,
    onClick: () -> Unit = {},
) {
    var isClosed by remember { mutableStateOf(true) } // 초기 상태: 창문 닫힘

    val painter = when {
        !isLocationGranted -> painterResource(R.drawable.img_close_window) // 권한 없으면 무조건 닫힘
        isClosed -> painterResource(R.drawable.img_close_window)
        else -> painterResource(R.drawable.img_sunny_window)
    }

    LaunchedEffect(forceOpen) {
        if (forceOpen) {
            isClosed = false
        }
    }

    if (!isLightOn) {
        Image(
            painter = painterResource(R.drawable.img_close_window),
            contentDescription = "닫힌 창문",
            modifier = modifier
                .offset(x = windowPosX, y = windowPosY)
                .size(120.dp)
        )
    } else {
        // 불 켜져있으면 열림/닫힘 상태 토글 가능
        val painter = if (isClosed) {
            painterResource(R.drawable.img_close_window)
        } else {
            when (weather) {
                WeatherType.SUNNY -> painterResource(R.drawable.img_sunny_window)
                WeatherType.CLOUDY -> painterResource(R.drawable.img_cloudy_window)
                WeatherType.RAINY -> painterResource(R.drawable.img_rainy_window)
                WeatherType.SNOWY -> painterResource(R.drawable.img_snowy_window)
                else -> painterResource(R.drawable.img_sunny_window)
            }
//            painterResource(R.drawable.img_sunny_window)
        }

        Image(
            painter = painter,
            contentDescription = if (isClosed) "닫힌 창문" else "열린 창문",
            modifier = modifier
                .offset(x = windowPosX, y = windowPosY)
                .size(120.dp)
                .noRippleClickable {
                    if (!isLocationGranted) {
                        // 권한 없으면 무조건 외부 콜백 실행 (다이얼로그 요청)
                        onClick()
                    } else if (isLightOn) {
                        // 권한 + 불 켜짐 → 열고 닫기 토글
                        isClosed = !isClosed
                        onClick()
                    }
                }
        )
    }
}
