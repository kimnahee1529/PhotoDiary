package com.todaylab.cleanarchtemplate.ui.widget.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.core.toAnimalImageRes
import com.todaylab.cleanarchtemplate.core.toColorEnum
import com.todaylab.cleanarchtemplate.core.toDrawableRes
import com.todaylab.cleanarchtemplate.ui.theme.colors

@Composable
fun LuckyResultContent(
    label: String?,
    value: String?,
    flippedCardIndex: Int? = null, // null이면 뒷면
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val isAnimalResult = label == stringResource(id = R.string.result_animal)
        val isColorResult = label == stringResource(id = R.string.result_color)

        if (flippedCardIndex != null) {
            // 앞면 (label만 보여줌)
            Text(
                text = "$label",
                fontFamily = FontFamily(Font(R.font.backdahyeon_font)),
                fontSize = 40.sp,
                color = MaterialTheme.colors.white
            )
        } else {
            // 뒷면
            when {
                isAnimalResult && !value.isNullOrEmpty() -> {
                    value.toAnimalImageRes()?.let { resId ->
                        Image(
                            painter = painterResource(id = resId),
                            contentDescription = value,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }

                isColorResult && !value.isNullOrEmpty() -> {
                    value.toColorEnum()?.let { colorEnum ->
                        Image(
                            painter = painterResource(id = colorEnum.toDrawableRes()),
                            contentDescription = value,
                            modifier = Modifier.size(80.dp)
                        )
                    }
                }

                else -> {
                    Text(
                        text = "$value",
                        fontFamily = FontFamily(Font(R.font.backdahyeon_font)),
                        fontSize = if (
                            label == stringResource(id = R.string.result_number) ||
                            label == stringResource(id = R.string.result_initial)
                        ) 40.sp else 24.sp,
                        color = MaterialTheme.colors.text2
                    )
                }
            }
        }
    }
}
