package com.todaylab.cleanarchtemplate.ui.widget

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.R
import com.todaylab.cleanarchtemplate.ui.model.LuckyResultState
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.util.floatingAnimationShared
import com.todaylab.cleanarchtemplate.ui.widget.card.FlipAnimationCard
import com.todaylab.cleanarchtemplate.ui.widget.card.LuckyItemCard
import java.time.LocalDate

// 확대, 블러 관련된 주석들은 카드 클릭했을 때 확대된 카드 만들기 위해서 작성해놓은 코드
@Composable
fun LuckyResultPropertiesDisplay(
    luckyResult: LuckyResultState,
    modifier: Modifier = Modifier
) {
//    var expandedLuckyResult by remember { mutableStateOf<LuckyResultState?>(null) } // 카드가 클릭되어 확대된 상태인지를 관리 ex)null:기본 카드, 값:확대됨
//    var blurActive by remember { mutableStateOf(false) } // 블러 효과를 줄지 말지 결정
    var flippedCardIndex by remember { mutableStateOf<Int?>(null) } // 어떤 카드가 뒤집혔는지 저장


//    val animatedBlurRadius by animateFloatAsState( // animateFloatAsState는 현재 상태에서 목표 상태까지 천천히 이동
//        targetValue = if (blurActive) 8f else 0f, // targetValue는 흐림의 정도
//        animationSpec = tween(durationMillis = 500), label = "blur_radius_animation"
//    )

    // 🌀 공유 루프 애니메이션 시간 값
    val transition = rememberInfiniteTransition()
    val cardTime by transition.animateFloat(
        initialValue = 0f,
        targetValue = (2 * Math.PI).toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing)
        ), label = "shared_time"
    )

//    LaunchedEffect(expandedLuckyResult) {
//        if (expandedLuckyResult != null) {
//            blurActive = true
//            delay(1000)
//            blurActive = false
//        } else {
//            blurActive = false
//        }
//    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
//                .blur(animatedBlurRadius.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlipAnimationCard(
                    isFlipped = flippedCardIndex == 0,
                    front = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_animal),
                            flippedCardIndex = 0,
                            onClick = { flippedCardIndex = 0 },
                            modifier = Modifier.floatingAnimationShared(cardTime, 0f)
                        )
                    },
                    back = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_animal),
                            value = luckyResult.animal,
                            onClick = { flippedCardIndex = null },
                            modifier = Modifier
                                .floatingAnimationShared(cardTime, 0f)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                FlipAnimationCard(
                    isFlipped = flippedCardIndex == 1,
                    front = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_number),
                            flippedCardIndex = 1,
                            onClick = { flippedCardIndex = 1 },
                            modifier = Modifier.floatingAnimationShared(cardTime, 1f)
                        )
                    },
                    back = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_number),
                            value = luckyResult.numbers.toString(),
                            onClick = { flippedCardIndex = null },
                            modifier = Modifier.floatingAnimationShared(cardTime, 1f)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FlipAnimationCard(
                    isFlipped = flippedCardIndex == 2,
                    front = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_initial),
                            flippedCardIndex = 2,
                            onClick = { flippedCardIndex = 2 },
                            modifier = Modifier.floatingAnimationShared(cardTime, 2f)
                        )
                    },
                    back = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_initial),
                            value = luckyResult.initials.joinToString(" "),
                            onClick = { flippedCardIndex = null },
                            modifier = Modifier.floatingAnimationShared(cardTime, 2f)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )

                FlipAnimationCard(
                    isFlipped = flippedCardIndex == 3,
                    front = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_color),
                            flippedCardIndex = 3,
                            onClick = { flippedCardIndex = 3 },
                            modifier = Modifier.floatingAnimationShared(cardTime, 3f)
                        )
                    },
                    back = {
                        LuckyItemCard(
                            label = stringResource(id = R.string.result_color),
                            value = luckyResult.color.toString(),
                            onClick = { flippedCardIndex = null },
                            modifier = Modifier.floatingAnimationShared(cardTime, 3f)
                        )
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// 프리뷰 함수
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewLuckyResultPropertiesDisplayWithAnimatedBlur() {
    LuckyTheme {
        val sampleResult = LuckyResultState(
            id = "1",
            date = LocalDate.now(),
            animal = "호랑이",
            numbers = 7,
            initials = listOf('ㅎ', 'ㅇ'),
            color = com.todaylab.cleanarchtemplate.domain.model.Color.RED
        )

        LuckyResultPropertiesDisplay(luckyResult = sampleResult)
    }
}
