package com.todaylab.photodiary.ui.diary

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.todaylab.photodiary.domain.model.WeatherType
import com.todaylab.photodiary.presentation.diary.DiaryEvent
import com.todaylab.photodiary.presentation.diary.DiaryViewModel
import com.todaylab.photodiary.presentation.diary.TimeType
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo
import com.todaylab.photodiary.ui.widget.dialog.DiaryDatePickerDialog
import com.todaylab.photodiary.ui.widget.dialog.DiaryTimePickerDialog
import com.todaylab.photodiary.ui.widget.picture.PhotoSelectorWithPreview
import com.todaylab.photodiary.ui.widget.textfield.BedTimeCell
import com.todaylab.photodiary.ui.widget.textfield.DateWeatherCell
import com.todaylab.photodiary.ui.widget.textfield.TitleCell
import com.todaylab.photodiary.ui.widget.textfield.UnderlineTextField
import com.todaylab.sketchmind.ui.widget.topappbar.CommonTopAppBar
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun DiaryEditRoute(
    popBackStack: () -> Unit = {},
    viewModel: DiaryViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
    val events = viewModel.events

    DiaryEditScreen(
        uiState = uiState,
        onPhotosSelected = { photos -> viewModel.updateSelectedPhotos(photos) },
        events = events,
        onClickDateCell = viewModel::onClickDateCell,
        onDatePicked = viewModel::onDatePicked,
        onClickWakeTime = viewModel::onClickWakeTime,
        onClickBedTime = viewModel::onClickBedTime,
        onTimePicked = viewModel::onTimePicked,
        onSaveClick = { title, weather, contentText ->
            viewModel.saveDiary(
                id = 0L,
                title = title,
                date = uiState.selectedDate,
                weather = weather,
                wakeTime = uiState.selectedWakeTime,
                sleepTime = uiState.selectedBedTime,
                content = contentText,
                photoUris = uiState.selectedPhotos.map { it.toString() }
            )
        },
        popBackStack = popBackStack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEditScreen(
    modifier: Modifier = Modifier,
    uiState: DiaryViewModel.DiaryUiState = DiaryViewModel.DiaryUiState(),
    navToResult: () -> Unit = {},
    onPhotosSelected: (List<Uri>) -> Unit = {},
    events: SharedFlow<DiaryEvent> = MutableSharedFlow<DiaryEvent>().asSharedFlow(),
    onClickDateCell: () -> Unit = {},
    onDatePicked: (LocalDate) -> Unit = {},
    onClickWakeTime: () -> Unit = {},
    onClickBedTime: () -> Unit = {},
    onTimePicked: (TimeType, LocalTime) -> Unit = { _, _ -> },
    onSaveClick: (String, WeatherType, String) -> Unit = { _, _, _ -> },
    popBackStack: () -> Unit = {},
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var dateInit by remember { mutableStateOf(uiState.selectedDate) }

    var timeInit by remember { mutableStateOf(uiState.selectedWakeTime) }
    var timeType by remember { mutableStateOf(TimeType.Wake) }
    var title by remember { mutableStateOf("") }
    var weather by remember { mutableStateOf(WeatherType.ETC) }

    var contentText by remember { mutableStateOf("") }

    LaunchedEffect(events) {
        events.collect { e ->
            when (e) {
                is DiaryEvent.OpenDatePicker -> {
                    dateInit = e.initial
                    showDatePicker = true
                }

                is DiaryEvent.OpenTimePicker -> {
                    timeType = e.type
                    timeInit = e.initial
                    showTimePicker = true
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.background(color = MaterialTheme.colors.white),
        topBar = {
            CommonTopAppBar(
                title = title,
                style = MaterialTheme.typo.head3,
                onBackClick = { popBackStack() },
                onActionClick = { onSaveClick(title, weather, contentText) },
                actionButtonText = "저장"
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .background(MaterialTheme.colors.white)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                DateWeatherCell(
                    date = uiState.selectedDate,
                    onClick = onClickDateCell,
                    onWeatherChange = { weather = it }
                )
                BedTimeCell(
                    selectedWakeTime = uiState.selectedWakeTime,
                    selectedBedTime = uiState.selectedBedTime,
                    onClickWakeTime = onClickWakeTime,
                    onClickBedTime = onClickBedTime
                )
                PhotoSelectorWithPreview(
                    selectedPhotos = uiState.selectedPhotos,
                    onPhotosSelected = onPhotosSelected,
                )
                TitleCell(
                    onTitleChange = { title = it }
                )
            }
            item {
                Spacer(modifier = Modifier.height(12.dp))
//                ContentCell()
//                UnderlineTextField()

                UnderlineTextField(
                    value = contentText,
                    onValueChange = { contentText = it },
                    hint = "여기에 입력하세요"
                )
            }
        }
    }

    DiaryDatePickerDialog(
        visible = showDatePicker,
        initialDate = dateInit,
        onConfirm = onDatePicked,
        onDismiss = { showDatePicker = false }
    )

    DiaryTimePickerDialog(
        visible = showTimePicker,
        initialTime = timeInit,
        is24Hour = true,
        onConfirm = { picked -> onTimePicked(timeType, picked) },
        onDismiss = { showTimePicker = false }
    )
}

@Preview(showBackground = true)
@Composable
fun DiaryEditScreenPreview() {
    ExampleTheme {
        DiaryEditScreen()
    }
}