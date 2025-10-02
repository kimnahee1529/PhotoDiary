package com.todaylab.photodiary.ui.magic

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.todaylab.photodiary.R
import com.todaylab.photodiary.presentation.magic.MagicBookViewModel
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.util.noRippleClickable
import com.todaylab.sketchmind.ui.widget.topappbar.CommonTopAppBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun MagicBookRoute(
    popBackStack: () -> Unit = {},
    viewModel: MagicBookViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    MagicBookScreen(
        uiState = uiState,
        onRequestSolution = {
            viewModel.viewModelScope.launch {
                viewModel.getSolution()
            }
        },
        popBackStack = popBackStack,
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MagicBookScreen(
    uiState: MagicBookViewModel.MagicBookUiState = MagicBookViewModel.MagicBookUiState(),
    onRequestSolution: () -> Unit = {},
    popBackStack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // 로컬 상태 (오늘 처음 보는 경우에만 사용)
    var isCentered by remember { mutableStateOf(false) }
    var showBubble by remember { mutableStateOf(true) }
    var showEffect by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    // 책이 확대되고 나서 텍스트 나오게 딜레이
    LaunchedEffect(isCentered) {
        if (isCentered && uiState.solution.isEmpty()) {
            delay(500)
            showEffect = true   // 🔹 effect 먼저 보여줌
            delay(2000)         // effect가 충분히 보이는 시간
            showEffect = false
            onRequestSolution()
            showText = true     // 바로 text로 전환됨
        }
    }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colors.white),
        topBar = {
            CommonTopAppBar(
                title = "해법의 책 화면",
                style = MaterialTheme.typo.titleSmall,
                onBackClick = { popBackStack() },
                onActionClick = {},
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.white)
                .padding(innerPadding)
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxSize()
            ) {
                val bubbleTop = maxHeight * 0.12f
                val bubbleHeight = maxHeight * 0.3f

                // =============== 오늘 처음 보는 경우만 말풍선 보여주기 ===============
                if (uiState.solution.isEmpty()) {
                    AnimatedVisibility(
                        visible = showBubble,
                        exit = fadeOut(animationSpec = tween(800)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = bubbleTop)
                            .height(bubbleHeight)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .noRippleClickable {
                                    showBubble = false
                                    isCentered = true
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.img_speech_bubble_small_bg),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize()
                            )
                            Text(
                                text = "질문을 생각하고\n 클릭해보세요",
                                style = MaterialTheme.typo.gowunH1,
                                color = MaterialTheme.colors.text1,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                val density = LocalDensity.current
                val baseOffsetY = with(density) { 200.dp.toPx() }

                val finalOffsetY: Float
                val targetScale: Float

                if (uiState.solution.isNotEmpty()) {
                    // 이미 해답을 본 경우 → 애니메이션 없이 바로 확대 상태
                    finalOffsetY = 0f
                    targetScale = 1.5f
                } else {
                    // 오늘 처음 보는 경우 → 애니메이션 적용
                    val targetOffsetY by animateDpAsState(
                        targetValue = if (isCentered) 0.dp else 200.dp,
                        animationSpec = tween(1000),
                        label = "offsetY"
                    )

                    val infiniteTransition = rememberInfiniteTransition(label = "floatAnim")
                    val floatOffset by infiniteTransition.animateFloat(
                        initialValue = -20f,
                        targetValue = 20f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1500, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "floatOffset"
                    )

                    val animatedScale by animateFloatAsState(
                        targetValue = if (isCentered) 1.5f else 1.0f,
                        animationSpec = tween(1000),
                        label = "scale"
                    )

                    finalOffsetY =
                        if (isCentered) with(density) { targetOffsetY.toPx() }
                        else baseOffsetY + floatOffset
                    targetScale = animatedScale
                }
                // =============== 책 + 해답 텍스트 ===============
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_magic_book_open),
                        contentDescription = null,
                        modifier = Modifier.graphicsLayer(
                            translationY = finalOffsetY,
                            scaleX = targetScale,
                            scaleY = targetScale
                        ),
                        contentScale = ContentScale.Crop
                    )

                    // TODO : 제대로 동작 안해서 다시 확인 해야 함, effect가 안 나옴
                    Crossfade(
                        targetState = when {
                            showEffect -> "effect"
                            showText || uiState.solution.isNotEmpty() -> "text"
                            else -> "empty" // 아무것도 안 보일 때는 empty
                        },
                        animationSpec = tween(500)
                    ) { state ->
                        when (state) {
                            "effect" -> {
//                                Timber.d("Crossfade: effect")
                                Image(
                                    painter = painterResource(R.drawable.img_magic_book_effect),
                                    contentDescription = null,
                                    modifier = Modifier.matchParentSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            "text" -> {
//                                Timber.d("Crossfade: text")
                                Text(
                                    text = uiState.solution,
                                    style = MaterialTheme.typo.gowunH1,
                                    color = MaterialTheme.colors.text1,
                                    textAlign = TextAlign.Center
                                )
                            }

                            else -> {
//                                Timber.d("Crossfade: empty")
                                Box(Modifier.size(0.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BookOpening(onFinish: () -> Unit) {
    var showSmog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showSmog = true
        delay(3000) // 2초 후 다음 화면
        showSmog = false
        onFinish()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.black.copy(alpha = 0.7f)),
        contentAlignment = Alignment.Center
    ) {

        // 🔥 스모그에 페이드 인/아웃
        AnimatedVisibility(
            visible = showSmog,
            enter = fadeIn(
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing
                )
            ),
            exit = fadeOut(
                animationSpec = tween(
                    durationMillis = 1000,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            Image(
                painter = painterResource(R.drawable.img_magic_book_open),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
            Image(
                painter = painterResource(R.drawable.img_magic_smog),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }
    }
}


@Composable
fun BookResult() {
    var showBook by remember { mutableStateOf(false) }
    var showEffect by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }

    // 2초 후 효과 사라지고 텍스트 보이기
    LaunchedEffect(Unit) {
        showBook = true
        showEffect = true
        delay(5000)
        showEffect = false
        showText = true
    }

    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        // 📌 기본 책 이미지
//        Image(
//            painter = painterResource(R.drawable.img_magic_book_open),
//            contentDescription = null,
//            modifier = Modifier.matchParentSize(),
//            contentScale = ContentScale.Crop
//        )
        AnimatedVisibility(
            visible = showBook,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut()
        ) {
            Image(
                painter = painterResource(R.drawable.img_magic_book_open),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .graphicsLayer(
                        scaleX = 1.3f, // 가로 10% 확대
                        scaleY = 1.3f  // 세로 10% 확대
                    ),
                contentScale = ContentScale.Crop
            )
        }

        // 📌 효과 이미지 (페이드 인 → 페이드 아웃)
        AnimatedVisibility(
            visible = showEffect,
            enter = fadeIn(animationSpec = tween(3000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            Image(
                painter = painterResource(R.drawable.img_magic_book_effect),
                contentDescription = null,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 📌 해답 텍스트 (효과 끝난 후 페이드 인)
        AnimatedVisibility(
            visible = showText,
            enter = fadeIn(animationSpec = tween(3000)),
            exit = fadeOut() // 필요 없으면 제거
        ) {
            Text(
                text = "여기에 해답 텍스트!",
                style = MaterialTheme.typo.titleMedium,
                color = MaterialTheme.colors.text1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ExampleTheme {
        MagicBookScreen()
    }
}