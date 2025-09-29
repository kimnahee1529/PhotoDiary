package com.todaylab.photodiary.ui.widget.textfield

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.isFinite
import com.todaylab.photodiary.domain.model.WeatherType
import com.todaylab.photodiary.ui.home.WeatherIcon
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.typo
import kotlinx.coroutines.android.awaitFrame
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.floor
import kotlin.math.max

private val DefaultCaretWidthDp = 2.dp

@Composable
fun UnderlineTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    hint: String = ""
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = false, // ✅ 여러 줄 입력 가능
        maxLines = Int.MAX_VALUE, // ✅ 무제한 줄바꿈 허용
        textStyle = LocalTextStyle.current.copy(
            color = MaterialTheme.colorScheme.onSurface
        ),
        modifier = modifier.fillMaxWidth(),
        decorationBox = { innerTextField ->
            Column {
                Box(
                    Modifier
                        .padding(vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hint,
                            style = LocalTextStyle.current.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        )
                    }
                    innerTextField() // ✅ 입력한 글자가 자동 줄바꿈됨
                }
                // 밑줄
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

@Composable
fun ContentCell(
    modifier: Modifier = Modifier,
    lineHeight: Dp = 40.dp,   // 한 줄 높이
    lineSpacing: Dp = 8.dp,    // 줄 간격
    onValueChange: (String) -> Unit = {},
) {
    // 각 줄의 텍스트
    val lines = remember { mutableStateListOf("") }

    // 포커스/스크롤/측정 홀더
    val focusReqs = remember { mutableStateListOf<FocusRequester>() }
    val bringReqs = remember { mutableStateListOf<BringIntoViewRequester>() }
    val lineWidthsPx = remember { mutableStateListOf<Float>() }

    // 줄 추가 후 다음 프레임에 포커스/스크롤을 옮기기 위한 인덱스
    var pendingFocusIndex by remember { mutableStateOf<Int?>(null) }

    fun ensureHolders() {
        while (focusReqs.size < lines.size) focusReqs += FocusRequester()
        while (bringReqs.size < lines.size) bringReqs += BringIntoViewRequester()
        while (lineWidthsPx.size < lines.size) lineWidthsPx += 0f
    }

    // 새 줄 생성 직후(컴포지션/레이아웃 이후) 포커스 + 스크롤 이동
    LaunchedEffect(lines.size, pendingFocusIndex) {
        val idx = pendingFocusIndex ?: return@LaunchedEffect
        if (idx in lines.indices) {
            awaitFrame()
            bringReqs[idx].bringIntoView()
            focusReqs[idx].requestFocus()
        }
        pendingFocusIndex = null
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        ensureHolders()

        lines.forEachIndexed { index, _ ->
            OneLineFieldMeasured(
                value = lines[index],
                availableWidthPx = lineWidthsPx[index],
                focusRequester = focusReqs[index],
                bringIntoViewRequester = bringReqs[index],
                lineHeight = lineHeight,
                onGloballyPositioned = { coords ->
                    val w = coords.size.width.toFloat()
                    if (w > 0f && lineWidthsPx[index] != w) lineWidthsPx[index] = w
                },
                // 값 변경(엔터 포함)
                onValueChange = { new ->
                    val nl = new.indexOf('\n') // 필요시 '\r'도 함께 검사 가능
                    if (nl >= 0) {
                        val head = new.substring(0, nl)
                        val tail = new.substring(nl + 1)
                        if (lines[index] != head) lines[index] = head
                        if (index == lines.lastIndex) lines += ""   // 새 줄 생성
                        lines[index + 1] = tail + lines[index + 1]
                        ensureHolders()
                        pendingFocusIndex = index + 1
                    } else {
                        lines[index] = new
                    }
                    onValueChange(new)
                },
                // 자동 줄바꿈(오버플로우 컷)
                onOverflowCut = { cut ->
                    val cur = lines[index]
                    if (cut in 1..cur.length) {
                        val head = cur.substring(0, cut)
                        val tail = cur.substring(cut)
                        if (lines[index] != head) lines[index] = head
                        if (index == lines.lastIndex) lines += ""
                        lines[index + 1] = tail + lines[index + 1]
                        ensureHolders()
                        pendingFocusIndex = index + 1
                    }
                }
            )

            if (index != lines.lastIndex) Spacer(Modifier.height(lineSpacing))
        }
    }
}

@Composable
private fun OneLineFieldMeasured(
    value: String,
    availableWidthPx: Float,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    lineHeight: Dp,
    onGloballyPositioned: (LayoutCoordinates) -> Unit,
    onValueChange: (String) -> Unit,
    onOverflowCut: (cutIndex: Int) -> Unit,
) {
    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current
    var handlingOverflow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(lineHeight)
            .bringIntoViewRequester(bringIntoViewRequester)
            .onGloballyPositioned(onGloballyPositioned)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange, // 여기서 '\n' 감지 → 다음 줄로 이동
            singleLine = false,            // IME 줄바꿈 키 유도
            maxLines = 1,                  // 시각적으로는 한 줄만 보이도록
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.None,
                keyboardType = KeyboardType.Text
            ),
            // 일부 키보드는 실제 '\n'을 안 넣기도 하므로 Enter를 강제로 주입
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .onPreviewKeyEvent { e ->
                    if (e.type == KeyEventType.KeyUp && e.key == Key.Enter) {
                        onValueChange(value + "\n")
                        true
                    } else false
                }
                .padding(vertical = 4.dp)
                .drawWithContent {
                    drawContent()
                    // 아래 물결 라인 (원고지 느낌)
                    clipRect {
                        val strokeWidth = 1.5.dp.toPx()
                        val y = size.height - strokeWidth / 2f
                        val amp = 1.dp.toPx()
                        val wave = 100.dp.toPx()
                        val path = Path().apply {
                            moveTo(0f, y);
                            var x = 0f
                            while (x < size.width) {
                                quadraticBezierTo(x + wave / 4, y - amp, x + wave / 2, y)
                                quadraticBezierTo(x + wave * 3 / 4, y + amp, x + wave, y)
                                x += wave
                            }
                        }
                        drawPath(path, Color.Gray, style = Stroke(width = strokeWidth))
                    }
                },
        )

        // 실제 가용 폭 기준으로 넘침 측정 → 컷 & 다음 줄 이동
        LaunchedEffect(value, availableWidthPx) {
            if (handlingOverflow) return@LaunchedEffect
            if (availableWidthPx <= 0f) return@LaunchedEffect

            // 커서(캐럿) 폭만큼 여유를 두고 측정 (겹침 방지)
            val caretPx = with(density) { DefaultCaretWidthDp.toPx() }
            val maxW = (availableWidthPx - caretPx).coerceAtLeast(0f).toInt()

            val layout = textMeasurer.measure(
                text = AnnotatedString(value),
                maxLines = 1,
                softWrap = false,
                overflow = TextOverflow.Clip,
                constraints = Constraints(
                    maxWidth = maxW,
                    maxHeight = Constraints.Infinity
                )
            )
            if (layout.didOverflowWidth) {
                var cut = layout.getLineEnd(0, visibleEnd = true)
                // 그래프림(문자 경계)로 보정 (한글/이모지 쪼개짐 방지)
                cut = previousGraphemeBoundary(value, cut)
                if (cut in 1..value.length) {
                    handlingOverflow = true
                    onOverflowCut(cut)
                    handlingOverflow = false
                }
            }
        }
    }
}

private fun previousGraphemeBoundary(text: CharSequence, index: Int): Int {
    if (index <= 0 || index > text.length) return index.coerceIn(0, text.length)
    val bi = java.text.BreakIterator.getCharacterInstance()
    bi.setText(text.toString())
    val boundary = bi.preceding(index)
    return if (boundary == java.text.BreakIterator.DONE) index - 1 else boundary
}

@Composable
private fun OneLineField(
    value: String,
    onValueChange: (String) -> Unit,
    onOverflowCut: (cutIndex: Int) -> Unit,
    focusRequester: FocusRequester,
) {
    var handlingOverflow by remember { mutableStateOf(false) }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { onValueChange("$value\n") } // IME Next 눌러도 줄바꿈 처리
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .focusRequester(focusRequester)
            .padding(vertical = 4.dp)
            .drawWithContent {
                drawContent()
                // 물결 라인(생략 가능)
                clipRect {
                    val strokeWidth = 1.5.dp.toPx()
                    val y = size.height - strokeWidth / 2f
                    val amp = 1.dp.toPx()
                    val wave = 100.dp.toPx()
                    val path = Path().apply {
                        moveTo(0f, y);
                        var x = 0f
                        while (x < size.width) {
                            quadraticBezierTo(x + wave / 4, y - amp, x + wave / 2, y)
                            quadraticBezierTo(x + wave * 3 / 4, y + amp, x + wave, y)
                            x += wave
                        }
                    }
                    drawPath(path, Color.Gray, style = Stroke(width = strokeWidth))
                }
            },
        onTextLayout = { layout ->
            if (!handlingOverflow && layout.hasVisualOverflow) {
                val cut = layout.getLineEnd(0, true)
                if (cut in 1..value.length) {
                    handlingOverflow = true
                    onOverflowCut(cut)
                    handlingOverflow = false
                }
            }
        }
    )
}


@Composable
fun FlowingFields(
    modifier: Modifier = Modifier,
    minLines: Int = 10,             // 기본 줄 수(필요시 더 크게)
    lineHeight: Dp = 40.dp          // 각 줄의 높이 (원고지 칸 높이 느낌)
) {
    val focusManager = LocalFocusManager.current
    val lines = remember { mutableStateListOf("") }

    // 최소 줄 수 확보
    LaunchedEffect(minLines) {
        while (lines.size < minLines) lines.add("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 12.dp)
    ) {
        lines.forEachIndexed { index, _ ->
            LineTextField(
                value = lines[index],
                onValueChange = { new -> lines[index] = new },
                onOverflow = { cutIndex ->
                    // cutIndex 이후의 글자를 다음 줄로 넘김
                    val cur = lines[index]
                    if (cutIndex in 0..cur.length) {
                        val head = cur.substring(0, cutIndex)
                        val tail = cur.substring(cutIndex)

                        if (tail.isNotEmpty()) {
                            // 현재 줄 잘라서 저장
                            if (lines[index] != head) lines[index] = head
                            // 다음 줄 보장
                            if (index == lines.lastIndex) lines.add("")
                            // 다음 줄 앞에 붙이기
                            lines[index + 1] = tail + lines[index + 1]
                        }
                    }
                },
                onImeNext = {
                    // 사용자가 Next 눌렀을 때 다음 줄로 포커스 이동
                    if (index == lines.lastIndex) lines.add("")
                    focusManager.moveFocus(FocusDirection.Down)
                },
                lineHeight = lineHeight
            )

            if (index != lines.lastIndex) Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun LineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onOverflow: (cutIndex: Int) -> Unit,
    onImeNext: () -> Unit,
    lineHeight: Dp
) {
    // 무한 루프 방지용 플래그
    var handlingOverflow by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(lineHeight)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { text ->
                onValueChange(text)
                // 줄바꿈 문자가 들어오면 다음 줄로 강제 이동
                val nl = text.indexOf('\n')
                if (nl >= 0) {
                    onOverflow(nl + 1) // 개행까지 잘라서 다음 줄로
                }
            },
            singleLine = true,
            textStyle = MaterialTheme.typo.labelSmall.copy(color = Color.Black),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { onImeNext() }),
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)
                .drawWithContent {
                    drawContent()
                    // 아래 물결 라인
                    clipRect {
                        val strokeWidth = 1.5.dp.toPx()
                        val y = size.height - strokeWidth / 2f
                        val amplitude = 1.dp.toPx()
                        val waveLength = 100.dp.toPx()
                        val path = Path().apply {
                            moveTo(0f, y)
                            var x = 0f
                            while (x < size.width) {
                                quadraticBezierTo(
                                    x + waveLength / 4f, y - amplitude,
                                    x + waveLength / 2f, y
                                )
                                quadraticBezierTo(
                                    x + waveLength * 3f / 4f, y + amplitude,
                                    x + waveLength, y
                                )
                                x += waveLength
                            }
                        }
                        drawPath(path, Color.Gray, style = Stroke(width = strokeWidth))
                    }
                },
            onTextLayout = { layout ->
                if (!handlingOverflow && layout.hasVisualOverflow) {
                    // 현재 줄 너비를 넘었으면 자를 위치 계산
                    val cut = layout.getLineEnd(0, true) // 화면에 보이는 마지막 인덱스
                    handlingOverflow = true
                    onOverflow(cut)
                    handlingOverflow = false
                }
            }
        )
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun AutoManuscriptGridField(
    modifier: Modifier = Modifier,
    cellSize: Dp = 40.dp,
    rowSpacing: Dp = 5.dp,
    stroke: Dp = 1.dp,
    lineColor: Color = Color.Gray,
    showLeadingGuide: Boolean = true,
    guideOffsetFraction: Float = 0f,
    heightFraction: Float = 0.45f,
    onTextChange: (String) -> Unit = {}
) {
    val conf = LocalConfiguration.current
    val screenW = conf.screenWidthDp.dp
    val screenH = conf.screenHeightDp.dp

    BoxWithConstraints(modifier = modifier) {
        val maxW = if (maxWidth.isFinite) maxWidth else screenW
        val maxH = if (maxHeight.isFinite) maxHeight else screenH * heightFraction

        val columns = max(1, floor(maxW / cellSize).toInt())
        val rows = max(1, floor((maxH + rowSpacing) / (cellSize + rowSpacing)).toInt())
        val gridHeight: Dp = (cellSize * rows) + (rowSpacing * (rows - 1))

        val total = columns * rows
        var cells by remember(columns, rows) { mutableStateOf(List(total) { TextFieldValue("") }) }

        val requesters = remember(columns, rows) { List(total) { FocusRequester() } }
        val bringers = remember(columns, rows) { List(total) { BringIntoViewRequester() } }

        // 📌 다음에 포커스 줄 칸
        var pendingFocus by remember(columns, rows) { mutableStateOf<Int?>(null) }

        LaunchedEffect(cells) { onTextChange(cells.joinToString("") { it.text }) }

        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            verticalArrangement = Arrangement.spacedBy(rowSpacing),
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(gridHeight)
                .border(stroke, lineColor)
        ) {
            items(total, key = { it }) { idx ->
                ManuscriptCell(
                    value = cells[idx],
                    onValueChange = { newVal ->
                        val clamped = clampToOneChar(newVal)
                        cells = cells.toMutableList().also { it[idx] = clamped }

                        // 조합 중이면 이동하지 않음
                        if (newVal.composition != null) return@ManuscriptCell

                        val raw = newVal.text
                        if (raw.isNotEmpty()) {
                            val updated = cells.toMutableList()
                            updated[idx] = TextFieldValue(
                                text = raw.first().toString(),
                                selection = TextRange(1)
                            )
                            var pos = idx + 1
                            for (ch in raw.drop(1)) {
                                if (pos >= total) break
                                updated[pos] = TextFieldValue(
                                    text = ch.toString(),
                                    selection = TextRange(1)
                                )
                                pos++
                            }
                            cells = updated

                            // 👉 다음 칸으로 이동 예약
                            pendingFocus = (idx + raw.length).coerceAtMost(total - 1)
                        }
                    },
                    onBackspaceAtEmpty = {
                        val prev = (idx - 1).coerceAtLeast(0)
                        if (prev != idx) {
                            cells = cells.toMutableList().also { it[prev] = TextFieldValue("") }
                            pendingFocus = prev
                        }
                    },
                    onDeleteCurrent = {
                        if (cells[idx].text.isNotEmpty()) {
                            cells = cells.toMutableList().also { it[idx] = TextFieldValue("") }
                        } else {
                            val prev = (idx - 1).coerceAtLeast(0)
                            if (prev != idx) {
                                cells = cells.toMutableList().also { it[prev] = TextFieldValue("") }
                                pendingFocus = prev
                            }
                        }
                    },
                    cellHeight = cellSize,
                    stroke = stroke,
                    lineColor = lineColor,
                    focusRequester = requesters[idx],
                    bringIntoViewRequester = bringers[idx],
                    isFirstColumn = idx % columns == 0,
                    isRightEdge = (idx + 1) % columns == 0,
                    isBottomEdge = idx >= total - columns,
                    showLeadingGuide = showLeadingGuide,
                    guideOffsetFraction = guideOffsetFraction,
                    onImeNext = {
                        val next = (idx + 1).coerceAtMost(total - 1)
                        pendingFocus = next
                    }
                )
            }
        }

        // ✅ 재구성 후 한 프레임 기다렸다가 포커스 이동
        LaunchedEffect(pendingFocus) {
            val target = pendingFocus ?: return@LaunchedEffect
            awaitFrame() // 한 프레임 대기
            requesters[target].requestFocus()
            bringers[target].bringIntoView()
            pendingFocus = null
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ManuscriptCell(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onBackspaceAtEmpty: () -> Unit,
    onDeleteCurrent: () -> Unit,
    cellHeight: Dp,
    stroke: Dp,
    lineColor: Color,
    focusRequester: FocusRequester,
    bringIntoViewRequester: BringIntoViewRequester,
    isFirstColumn: Boolean,
    isRightEdge: Boolean,
    isBottomEdge: Boolean,
    showLeadingGuide: Boolean,
    guideOffsetFraction: Float,
    onImeNext: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .drawBehind {
                val sw = stroke.toPx()
                if (showLeadingGuide && isFirstColumn) {
                    val x = if (guideOffsetFraction <= 0f) sw
                    else (size.width * guideOffsetFraction).coerceIn(0f, size.width)
                    drawLine(lineColor, Offset(x, 0f), Offset(x, size.height), sw)
                    drawLine(lineColor, Offset(0f, 0f), Offset(size.width, 0f), sw)
                    drawLine(lineColor, Offset(size.width, 0f), Offset(size.width, size.height), sw)
                    drawLine(
                        lineColor,
                        Offset(0f, size.height),
                        Offset(size.width, size.height),
                        sw
                    )
                } else {
                    drawRect(color = lineColor, style = Stroke(width = sw))
                }
            },
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = value,
            onValueChange = { tfv -> onValueChange(clampToOneChar(tfv)) },
            singleLine = true,
            cursorBrush = SolidColor(Color.Black),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(onNext = { onImeNext() }),
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester)
                .focusTarget() // focusRequester 뒤에 와야 함
                .bringIntoViewRequester(bringIntoViewRequester)
                .focusable()
                .onPreviewKeyEvent { e ->
                    if (e.type == KeyEventType.KeyDown && e.key == Key.Backspace) {
                        if (value.composition != null) false
                        else if (value.text.isEmpty()) {
                            onBackspaceAtEmpty(); true
                        } else {
                            onDeleteCurrent(); true
                        }
                    } else false
                }
                .onKeyEvent { e ->
                    if (e.type == KeyEventType.KeyDown && e.key == Key.Backspace) {
                        if (value.composition != null) false
                        else if (value.text.isEmpty()) {
                            onBackspaceAtEmpty(); true
                        } else {
                            onDeleteCurrent(); true
                        }
                    } else false
                },
            decorationBox = { inner ->
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(2.dp),
                    contentAlignment = Alignment.Center
                ) { inner() }
            }
        )
    }
}

/** 항상 1글자로 잘라서 반환(조합 중이면 범위 유지) */
private fun clampToOneChar(tfv: TextFieldValue): TextFieldValue {
    val t = tfv.text.take(1)
    return TextFieldValue(
        text = t,
        selection = TextRange(t.length),
        composition = tfv.composition?.let { TextRange(0, t.length) }
    )
}

@Composable
fun DateWeatherCell(
    date: LocalDate = LocalDate.now(),
    onClick: () -> Unit = {},
    onWeatherChange: (WeatherType) -> Unit = {},
) {
    var selectedWeather by remember { mutableStateOf<WeatherType?>(null) }

    val dateText = remember(date) {
        date.format(DateTimeFormatter.ofPattern("MM월 dd일 E요일"))
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Gray)
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable { onClick() },
            text = dateText,
        )
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "날씨")
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                WeatherIcon(
                    type = WeatherType.SUNNY,
                    selectedWeather = selectedWeather,
                        onSelect = {
                            selectedWeather = it
                            selectedWeather?.let { weather ->
                                onWeatherChange(weather)
                            }
                        }
                )
                WeatherIcon(
                    type = WeatherType.CLOUDY,
                    selectedWeather = selectedWeather,
                    onSelect = {
                        selectedWeather = it
                        selectedWeather?.let { weather ->
                            onWeatherChange(weather)
                        }
                    }
                )
                WeatherIcon(
                    type = WeatherType.RAINY,
                    selectedWeather = selectedWeather,
                    onSelect = {
                        selectedWeather = it
                        selectedWeather?.let { weather ->
                            onWeatherChange(weather)
                        }
                    }
                )
                WeatherIcon(
                    type = WeatherType.SNOWY,
                    selectedWeather = selectedWeather,
                    onSelect = {
                        selectedWeather = it
                        selectedWeather?.let { weather ->
                            onWeatherChange(weather)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BedTimeCell(
    selectedWakeTime: LocalTime = LocalTime.of(7, 0),
    selectedBedTime: LocalTime = LocalTime.of(22, 0),
    onClickWakeTime: () -> Unit = {},
    onClickBedTime: () -> Unit = {},
) {

    val wakeText = remember(selectedWakeTime) {
        if (selectedWakeTime.minute == 0) {
            "${selectedWakeTime.hour}시"
        } else {
            "${selectedWakeTime.hour}시 ${selectedWakeTime.minute}분"
        }
    }
    val bedText = remember(selectedBedTime) {
        if (selectedBedTime.minute == 0) {
            "${selectedBedTime.hour}시"
        } else {
            "${selectedBedTime.hour}시 ${selectedBedTime.minute}분"
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onClickWakeTime()
                },
            text = "일어난 시간: $wakeText",
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onClickBedTime()
                },
            text = "잠드는 시간: $bedText",
        )
    }
}

@Composable
fun TitleCell(
    onTitleChange: (String) -> Unit = {},
) {

    var title by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color.Gray,
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "제목 : ",
        )
        BasicTextField(
            value = title,
            onValueChange = { newValue ->
                title = newValue
                onTitleChange(newValue)
            },
            modifier = Modifier.weight(1f)
        )
    }
}

//@Composable
//fun DiaryLayout(
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//    ) {
//        DateCell()
//        BedTimeCell()
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .aspectRatio(1f)
//                .border(1.dp, Color.Gray)
//        )
//
//        TitleCell()
//
//        AutoManuscriptGridField(
//            modifier = Modifier.weight(1f),
//            onTextChange = { /* ... */ }
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
private fun UnderlineTextFieldPreview() {
    ExampleTheme {
        UnderlineTextField(
            value = "테스트",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DateWeatherCellPreview() {
    ExampleTheme {
        DateWeatherCell()
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentCellPreview() {
    ExampleTheme {
        ContentCell()
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    ExampleTheme {
        Box(
            modifier = Modifier.padding(5.dp)
        ) {
            TitleCell(
                onTitleChange = { "이건 제목" }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ManuscriptGridFieldPreview() {
    ExampleTheme {
        AutoManuscriptGridField(
//            cellSize = 32.dp,
            onTextChange = { text ->
            }
        )
    }
}