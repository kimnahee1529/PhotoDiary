package com.todaylab.photodiary.ui.plant

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.photodiary.R
import com.todaylab.photodiary.presentation.plant.PlantViewModel
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.util.noRippleClickable
import com.todaylab.sketchmind.ui.widget.topappbar.CommonTopAppBar
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun PlantRoute(
    viewModel: PlantViewModel = hiltViewModel(),
    popBackStack: () -> Unit = {},
) {

    val uiState by viewModel.uiState.collectAsState()
    val streak = uiState._streak
    LaunchedEffect(streak) {
        Timber.d("며칠 연속?: $streak")
    }

    PlantScreen(
        uiState = uiState,
        popBackStack = popBackStack,
    )
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlantScreen(
    uiState: PlantViewModel.PlantUiState = PlantViewModel.PlantUiState(),
    popBackStack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var showDrawer by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.background(MaterialTheme.colors.white),
        topBar = {
            CommonTopAppBar(
                title = "식물 화면",
                style = MaterialTheme.typo.titleSmall,
                onBackClick = { popBackStack() },
                onActionClick = {}
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colors.white)
        ) {
            // ---------------- 메인 콘텐츠 ----------------
            if (!showDrawer) {
                IconButton(onClick = { showDrawer = true }) {
                    Icon(
                        painter = painterResource(R.drawable.img_plant_sprout),
                        contentDescription = "도장 보기"
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                // 책갈피 버튼 (Drawer 열기)

                // 화분 & 물뿌리개
                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize()
                ) {
                    val scope = rememberCoroutineScope()
                    val density = LocalDensity.current

                    // ------- 상태 ------- //
                    var isDragging by remember { mutableStateOf(false) }
                    var waterCount by remember { mutableStateOf(0) }
                    var previousIsOverSoil by remember { mutableStateOf(false) }

                    // ------- 초기 위치 ------- //
                    val canSize = 150.dp
                    val padTop = 8.dp
                    val padEnd = 0.dp

                    val initial: Offset = remember(maxWidth, maxHeight) {
                        with(density) {
                            Offset(
                                (maxWidth - canSize - padEnd).toPx(),
                                padTop.toPx()
                            )
                        }
                    }

                    val canOffset = remember(initial) {
                        Animatable(initial, Offset.VectorConverter)
                    }

                    // ------- 화분(식물) 위치 ------- //
                    val potSizePx = with(density) { 400.dp.toPx() }
                    val potTop = with(density) { maxHeight.toPx() } - potSizePx
                    val potWaterAreaTop = potTop
                    val potWaterAreaBottom = potTop + potSizePx / 5f

                    // ------- 물뿌리개 좌표 ------- //
                    val canCenterY = canOffset.value.y + with(density) { canSize.toPx() } / 2f
                    val isOverSoil = canCenterY in potWaterAreaTop..potWaterAreaBottom

                    // ------- 물 준 횟수 카운트 (false → true 전환 시) ------- //
                    LaunchedEffect(isOverSoil) {
                        if (!previousIsOverSoil && isOverSoil) {
                            waterCount++
                        }
                        previousIsOverSoil = isOverSoil
                    }

                    // ------- 물뿌리개 이미지 ------- //
                    val canPainter = when {
                        isOverSoil -> painterResource(R.drawable.img_watering_can_water)
                        isDragging -> painterResource(R.drawable.img_watering_can_empty)
                        else -> painterResource(R.drawable.img_watering_can)
                    }

                    // ------- 화분 이미지 선택 ------- //
                    val potPainter = when {
//                        waterCount >= 5 -> painterResource(R.drawable.img_plant_grown)
//                        waterCount >= 1 -> painterResource(R.drawable.img_plant_small)
                        else -> painterResource(R.drawable.img_plant_sprout)
                    }

                    // ------- 드래그 가능한 물뿌리개 ------- //
                    Image(
                        painter = canPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(canSize)
                            .offset {
                                IntOffset(
                                    canOffset.value.x.roundToInt(),
                                    canOffset.value.y.roundToInt()
                                )
                            }
                            .pointerInput(Unit) {
                                detectDragGestures(
                                    onDragStart = { isDragging = true },
                                    onDrag = { change, dragAmount ->
                                        change.consume()
                                        scope.launch {
                                            canOffset.snapTo(
                                                canOffset.value + Offset(dragAmount.x, dragAmount.y)
                                            )
                                        }
                                    },
                                    onDragEnd = {
                                        isDragging = false
                                        scope.launch {
                                            canOffset.animateTo(initial, animationSpec = tween(300))
                                        }
                                    },
                                    onDragCancel = {
                                        isDragging = false
                                        scope.launch {
                                            canOffset.animateTo(initial, animationSpec = tween(300))
                                        }
                                    }
                                )
                            }
                            .zIndex(1f)
                    )

                    // ------- 식물(화분) ------- //
                    Image(
                        painter = potPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(400.dp)
                            .align(Alignment.BottomCenter)
                            .zIndex(0f)
                    )

                    // ------- 물을 너무 많이 줬을 때 메시지 ------- //
                    if (waterCount >= 7) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopCenter)
                                .padding(top = 50.dp)
                        ) {
                            Text("물을 너무 많이 줬어요! 🌊")
                        }
                    }
                }
            }

            // ---------------- Drawer ----------------
            if (showDrawer) {
                // 반투명 배경 (바깥 클릭 시 닫힘)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.3f))
                        .noRippleClickable { showDrawer = false }
                )
            }

            AnimatedVisibility(
                visible = showDrawer,
                enter = slideInHorizontally(initialOffsetX = { -it }),
                exit = slideOutHorizontally(targetOffsetX = { -it })
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.TopStart) // AppBar 바로 밑에서 열림
                        .background(
                            Color(0xFFFCFCE2),
                            RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                        )
                        .padding(16.dp)
                ) {
                    // 닫기 버튼
                    IconButton(
                        onClick = { showDrawer = false },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "닫기")
                    }

                    // 도장 UI
                    StampScreen(
                        streak = uiState._streak,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ResultScreenPreview() {
    ExampleTheme {
        PlantScreen()
    }
}