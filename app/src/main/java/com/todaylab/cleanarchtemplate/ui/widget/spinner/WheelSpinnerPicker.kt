package com.todaylab.cleanarchtemplate.ui.widget.spinner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.widget.dialog.ConfirmButtonDialog


@Composable
fun WheelSpinnerPicker(
    modifier: Modifier = Modifier,
    label: String = "", // 텍스트 박스 뒤에 붙을 레이블
    text: String = "", // 텍스트 박스 값
    placeholderText: String = "", // 텍스트 박스 힌트
    spinnerItems: List<String> = emptyList(), // 스피너 항목 목록
    onSpinnerItemSelect: (String) -> Unit = {}, // 스피너 항목 선택 콜백 함수
) {
    var (showSpinnerDialog, setShowSpinnerDialog) = remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
                .background(
                    color = MaterialTheme.colors.white, shape = RoundedCornerShape(6.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.gray9,
                    shape = RoundedCornerShape(6.dp)
                )
                .clickable {
                    // show spinner dialog on box click
                    setShowSpinnerDialog(true)
                },
            contentAlignment = Alignment.CenterStart,
        ) {
            // show placeholder text if text is empty
            Text(
                text = if (text.isNotBlank()) text else placeholderText,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = if (text.isNotBlank()) Color.Black else Color.Gray,
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Bottom),
        )
    }

    // render spinner dialog at end of composable function
    if (showSpinnerDialog) {
        ConfirmButtonDialog(
            onDismissRequest = { setShowSpinnerDialog(false) },
            onConfirm = { setShowSpinnerDialog(false) },
            content = {
                WheelSpinner(
                    items = spinnerItems,
                    onItemSelect = onSpinnerItemSelect,
                )
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewWheelSpinnerPicker() {
    val (pickerValue, setPickerValue) = remember {
        mutableStateOf("")
    }

    LuckyTheme {
        WheelSpinnerPicker(
            label = "월",
            text = pickerValue,
            placeholderText = "태어난 월",
            onSpinnerItemSelect = setPickerValue,
            spinnerItems = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12")
        )
    }
}