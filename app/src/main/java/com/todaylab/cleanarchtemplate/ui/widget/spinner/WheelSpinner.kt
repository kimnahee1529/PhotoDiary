package com.todaylab.cleanarchtemplate.ui.widget.spinner

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WheelSpinner(
    items: List<String>,
    modifier: Modifier = Modifier,
    visibleItemsCount: Int = 5,
    onItemSelect: (String) -> Unit
) {
    val centerIndex = visibleItemsCount / 2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = centerIndex)

    Box(
        modifier = modifier
            .height((visibleItemsCount * 40).dp) //Spinner 전체 높이
            .fillMaxWidth()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            items(items.size) { index ->
                val isCenter = listState.firstVisibleItemIndex + centerIndex == index
                val fontSize = if (isCenter) 24.sp else 16.sp
                val alpha = if (isCenter) 1f else 0.1f
                val weight = if (isCenter) FontWeight.Bold else FontWeight.Normal

                Box(
                    modifier = Modifier
                        .height(40.dp) // 각 항목 높이
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[index],
                        fontSize = fontSize,
                        fontWeight = weight,
                        color = Color.Black.copy(alpha = alpha)
                    )
                }
            }
        }
    }

    // 스크롤 멈추면 가운데 항목 선택 + 정렬
    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val selectedIndex = listState.firstVisibleItemIndex + centerIndex
            if (selectedIndex in items.indices) {
                onItemSelect(items[selectedIndex])

                val targetIndex =
                    (selectedIndex - centerIndex).coerceIn(0, items.size - visibleItemsCount)
                listState.animateScrollToItem(targetIndex)

            }
        }
    }
}


@Preview
@Composable
private fun PreviewWheelSpinner() {
    val items = listOf("", "", "1번", "2번", "3번", "4번", "5번", "6번", "7번", "8번", "9번", "10번", "", "")
    var selected by remember { mutableStateOf("") }

    WheelSpinner(items = items, onItemSelect = { selected = it })
}
