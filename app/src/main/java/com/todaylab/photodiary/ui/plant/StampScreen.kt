package com.todaylab.photodiary.ui.plant

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.R
import com.todaylab.photodiary.ui.theme.ExampleTheme

@Composable
fun StampScreen(
    streak: Int,
    modifier: Modifier = Modifier
) {
    // 한 줄에 5개 배치 (원한다면 2행/3행으로도 가능)
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(5) { index ->
            if (index < streak) {
                // 도장 찍은 자리
                Image(
                    painter = painterResource(R.drawable.img_stamp), // 도장 이미지
                    contentDescription = null,
                    modifier = Modifier
                        .weight(1f) // Row 폭을 5등분
                        .aspectRatio(1f)
                )
            } else {
                // 빈 원 자리
                Box(
                    modifier = Modifier
                        .weight(1f) // Row 폭을 5등분
                        .aspectRatio(1f)
                        .background(Color(0xFFFFFF99), CircleShape) // 연한 노란색
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StampScreenPreview() {
    ExampleTheme {
        StampScreen(
            streak = 3,
            modifier = Modifier.background(Color.White)
        )
    }
}