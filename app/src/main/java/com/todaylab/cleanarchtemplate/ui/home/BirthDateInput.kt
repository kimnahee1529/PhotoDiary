package com.todaylab.cleanarchtemplate.ui.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.ui.widget.spinner.WheelSpinnerPicker

@Composable
fun BirthDateInput(
    year: String = "",
    month: String = "",
    day: String = "",
    onYearSelect: (String) -> Unit = { _ -> },
    onMonthSelect: (String) -> Unit = { _ -> },
    onDaySelect: (String) -> Unit = { _ -> },
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        WheelSpinnerPicker(
            modifier = Modifier.weight(1f),
            text = year,
            label = "년",
            placeholderText = "태어난 년도",
            onSpinnerItemSelect = onYearSelect,
            spinnerItems = (1900..2023).map { it.toString() }
        )
        Spacer(modifier = Modifier.width(4.dp))
        WheelSpinnerPicker(
            modifier = Modifier.weight(1f),
            text = month,
            label = "월",
            placeholderText = "태어난 달",
            onSpinnerItemSelect = onMonthSelect,
            spinnerItems = (1..12).map { it.toString() }
        )
        Spacer(modifier = Modifier.width(4.dp))
        WheelSpinnerPicker(
            modifier = Modifier.weight(1f),
            text = day,
            label = "일",
            placeholderText = "태어난 날",
            onSpinnerItemSelect = onDaySelect,
            spinnerItems = (1..31).map { it.toString() }
        )
    }
}

@Preview
@Composable
private fun PreviewBirthDateInput() {
    val (birthYear, setBirthYear) = remember {
        mutableStateOf("")
    }
    val (birthMonth, setBirthMonth) = remember {
        mutableStateOf("")
    }
    val (birthDay, setBirthDay) = remember {
        mutableStateOf("")
    }

    BirthDateInput(
        year = birthYear,
        month = birthMonth,
        day = birthDay,
        onYearSelect = setBirthYear,
        onMonthSelect = setBirthMonth,
        onDaySelect = setBirthDay
    )
}