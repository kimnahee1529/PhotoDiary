package com.todaylab.photodiary.ui.widget.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors
import com.todaylab.photodiary.ui.theme.typo

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    shape: RoundedCornerShape = RoundedCornerShape(0.dp),
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typo.body1
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {
        Text(
            text,
            style = textStyle
        )
    }
}

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    shape: RoundedCornerShape = RoundedCornerShape(12.dp),
    textStyle: androidx.compose.ui.text.TextStyle = MaterialTheme.typo.labelSmall
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .width(100.dp),
        shape = shape,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
    ) {
        Text(
            text,
            style = textStyle
        )
    }
}


@Composable
fun SkipButton(
    modifier: Modifier = Modifier,
    text: String,
    onSkipClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onSkipClick,
        containerColor = Color.Transparent,
        modifier = modifier
            .fillMaxWidth(),
        contentColor = MaterialTheme.colors.secondary
    )
}

@Composable
fun InputCompleteButton(
    modifier: Modifier = Modifier,
    text: String,
    onNextClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onNextClick,
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(top = 8.dp),
        shape = RoundedCornerShape(12.dp),
        containerColor = MaterialTheme.colors.primary,
        contentColor = Color.White,
        textStyle = MaterialTheme.typo.button
    )
}

@Composable
fun NextButton(
    modifier: Modifier = Modifier,
    @StringRes skipId: Int,
    @StringRes nextId: Int,
    onSkipClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            SkipButton(
                text = stringResource(id = skipId),
                onSkipClick = onSkipClick
            )
            InputCompleteButton(
                text = stringResource(id = nextId),
                onNextClick = onNextClick
            )
        }
    }
}

//다른 버튼 예시
@Composable
private fun TestCropButton(
    modifier: Modifier = Modifier,
    text: String,
    onSkipClick: () -> Unit = {}
) {
    CustomButton(
        text = text,
        onClick = onSkipClick,
        modifier = modifier,
        containerColor = MaterialTheme.colors.green6,
        shape = RoundedCornerShape(12.dp),
        contentColor = MaterialTheme.colors.secondary
    )
}

@Composable
fun LuckyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(60.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = colors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.button,
        )
    }
}

@Composable
fun LuckyDialogButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 80.dp)
            .height(50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        enabled = enabled,
        colors = colors
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.button,
        )
    }
}

@Composable
fun SmallTopButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.white
    )
) {
    Button(
        modifier = modifier
            .defaultMinSize(minWidth = 1.dp), // 너비 줄이기
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        colors = colors,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.labelSmall
        )
    }
}

@Composable
fun MediumTopButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.white
    )
) {
    Button(
        modifier = modifier
            .defaultMinSize(minWidth = 1.dp), // 너비 줄이기
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        enabled = enabled,
        colors = colors,
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typo.labelLarge
        )
    }
}


@Preview
@Composable
private fun ButtonPreview() {
    ExampleTheme {
        TestCropButton(text = "농작물")
    }
}

@Preview
@Composable
private fun DialogButtonPreview() {
    ExampleTheme {
        Row() {
            DialogButton(
                text = "취소",
                onClick = {},
                containerColor = MaterialTheme.colors.primary2,
                contentColor = MaterialTheme.colors.white
            )
            DialogButton(
                text = "만들기",
                onClick = {},
                containerColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.white
            )
        }
    }
}

@Preview
@Composable
private fun SkipButtonPreview() {
    SkipButton(text = "나중에 입력할게요")
}

@Preview
@Composable
private fun InputCompleteButtonPreview() {
    InputCompleteButton(text = "다음으로")
}

@Preview
@Composable
private fun LuckyButtonPreview() {
    ExampleTheme {
        LuckyButton(text = "농작물", {})
    }
}

@Preview
@Composable
private fun LuckyDialogButtonPreview() {
    ExampleTheme {
        LuckyDialogButton(text = "농작물", {})
    }
}

@Preview
@Composable
private fun SmallTopButtonPreview() {
    ExampleTheme {
        SmallTopButton(text = "방 만들기", {})
    }
}