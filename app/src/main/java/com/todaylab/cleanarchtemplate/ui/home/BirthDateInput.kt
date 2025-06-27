package com.todaylab.cleanarchtemplate.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.widget.WheelSpinner

@Composable
fun BirthDateInput(
    year: String = "",
    month: String = "",
    day: String = "",
    onYearChange: (String) -> Unit = { _ -> },
    onMonthChange: (String) -> Unit = { _ -> },
    onDayChange: (String) -> Unit = { _ -> },
    modifier: Modifier = Modifier
) {
    var spinnerDialogState by remember {
        mutableStateOf<SpinnerState?>(null)
    }
    // 실제 다이얼로그는 최하단에서 그려야 안전
    spinnerDialogState?.let { state ->
        CenteredButtonDialog(
            onDismissRequest = { spinnerDialogState = null },
            onConfirm = { spinnerDialogState = null },
            content = {
                WheelSpinner(
                    items = state.items,
                    onSelected = {
                        state.onSelected(it)
                    },
                )
            },
        )
    }

}

@Composable
fun WheelSpinnerSelector(
    modifier: Modifier = Modifier,
    savedText: String,
    selectedText: String,
    label: String = "",
    onClick: () -> Unit,
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .height(50.dp)
                .weight(1f)
                .background(
                    color = MaterialTheme.colors.grey2, shape = RoundedCornerShape(6.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = if (selectedText.isNotBlank()) {
                    selectedText
                } else if (savedText.isNotBlank()) {
                    savedText
                } else {
                    ""
                },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = if (selectedText.isNotBlank()) Color.Black else Color.Gray,
            )
        }
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.align(Alignment.Bottom),
        )
    }
}

@Composable
fun CenteredButtonDialog(
    onDismissRequest: () -> Unit,
    content: @Composable () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                content()

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onConfirm) {
                    Text("확인")
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewWheelSpinnerSelector() {
    (1980..2025).map { it.toString() }

    WheelSpinnerSelector(
        savedText = "2023",
        selectedText = "2023",
        label = "년",
        onClick = {},
    )
}
