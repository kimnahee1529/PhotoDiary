package com.todaylab.sketchmind.ui.widget.topappbar

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.widget.button.SmallTopButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBar(
    title: String? = null,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typo.titleLarge,
    onBackClick: (() -> Unit)? = null,
    actionButtonText: String? = null,
    onActionClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colors.white
        ),
        navigationIcon = {
            if (onBackClick != null) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기",
                    )
                }
            } else {
                Spacer(modifier = Modifier.size(48.dp))
            }
        },
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (title != null) {
                    Text(
                        text = title,
                        style = style,
                        color = MaterialTheme.colors.text1,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                    )
                }
//                else {
//                    TimedProgressBar(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(6.dp),
////                            .padding(horizontal = 16.dp),
//                        durationMillis = 5000,
//                        color = Color(0xFF2D5FFF)
//                    )
//                }
            }
        },
        actions = {
            if (actionButtonText != null) {
                SmallTopButton(
                    text = actionButtonText,
                    onClick = onActionClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colors.primary,
                        contentColor = MaterialTheme.colors.white
                    )
                )
            } else {
                // actions도 없으면 우측 공간 확보
                Spacer(modifier = Modifier.size(48.dp)) // TextButton 크기만큼
            }
        }
    )
}


@Composable
fun TimedProgressBar(
    modifier: Modifier = Modifier,
    durationMillis: Int = 120000, // 전체 제한 시간 (ms)
    color: Color = Color.Blue,
    gameStartTime: Long?, // 서버에서 받은 게임 시작 timestamp
    backgroundColor: Color = MaterialTheme.colors.primary2
) {
    val progress = remember { Animatable(0f) }

    LaunchedEffect(gameStartTime) {
        gameStartTime?.let {
            val elapsed = System.currentTimeMillis() - it
            val clampedElapsed = elapsed.coerceAtLeast(0)

            val initialProgress = (clampedElapsed.toFloat() / durationMillis)
                .coerceIn(0f, 1f)
            progress.snapTo(initialProgress)

            val remainingDuration = (durationMillis - clampedElapsed).coerceAtLeast(0)

            if (remainingDuration > 0) {
                progress.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(
                        durationMillis = remainingDuration.toInt(),
                        easing = LinearEasing
                    )
                )
            } else {
                progress.snapTo(1f)
            }
        } ?: run {
            progress.snapTo(0f)
        }
    }

    // UI
    Box(
        modifier = modifier
            .height(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress.value)
                .background(color)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonTopAppBarWithProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colors.white
        ),
        navigationIcon = {
            onBackClick?.let {
                IconButton(onClick = it) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "뒤로가기",
                    )
                }
            }
        },
        title = {
            Box(modifier = Modifier.fillMaxWidth())
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewRoomList() {
    ExampleTheme {
        CommonTopAppBar(
            title = "방 리스트",
//            onBackClick = {},
            actionButtonText = "저장",
            onActionClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGameScreen() {
    ExampleTheme {
        Column() {
            CommonTopAppBar(
                onBackClick = {},
                onActionClick = {},
            )
            TimedProgressBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                durationMillis = 5000, // 5초 동안 진행
                color = Color(0xFF2D5FFF), // 원하는 색
                gameStartTime = 1753627378428
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCommonTopAppBarWithProgress() {
    ExampleTheme {
        CommonTopAppBarWithProgress(
            progress = 0.5f,
            onBackClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTimedProgressBar() {
    ExampleTheme {
        TimedProgressBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            durationMillis = 5000, // 5초 동안 진행
            color = Color(0xFF2D5FFF), // 원하는 색
            gameStartTime = 1753627378428
        )
    }
}
