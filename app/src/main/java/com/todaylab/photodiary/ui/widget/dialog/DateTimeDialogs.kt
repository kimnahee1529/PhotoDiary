package com.todaylab.photodiary.ui.widget.dialog

import androidx.compose.foundation.layout.padding
import com.todaylab.photodiary.ui.util.toEpochMillisUtc
import com.todaylab.photodiary.ui.util.toLocalDateUtc

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.*

/** 날짜 선택 다이얼로그 (UTC 변환 가정) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDatePickerDialog(
    visible: Boolean,
    initialDate: LocalDate,
    onConfirm: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    yearRange: IntRange = 1900..2100,
) {
    if (!visible) return
    val pickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.toEpochMillisUtc(),
        yearRange = yearRange
    )
    DatePickerDialog(
        modifier = Modifier.padding(16.dp),
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                pickerState.selectedDateMillis?.let { millis ->
                    onConfirm(millis.toLocalDateUtc())
                }
                onDismiss()
            }) { Text("확인") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("취소") }
        }
    ) {
        DatePicker(state = pickerState)
    }
}

/** 시간 선택 다이얼로그 (Material3: TimePicker + AlertDialog) */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryTimePickerDialog(
    visible: Boolean,
    initialTime: LocalTime,
    is24Hour: Boolean = true,
    onConfirm: (LocalTime) -> Unit,
    onDismiss: () -> Unit,
) {
    if (!visible) return
    val timeState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = is24Hour
    )
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onConfirm(LocalTime.of(timeState.hour, timeState.minute))
                onDismiss()
            }) { Text("확인") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("취소") }
        },
        text = { TimePicker(state = timeState) }
    )
}
