package com.todaylab.cleanarchtemplate.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.theme.colors
import com.todaylab.cleanarchtemplate.ui.theme.typo

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
fun LuckyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors : ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
){
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
    colors : ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
){
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

@Preview
@Composable
private fun SkipButtonPreview() {
    SkipButton(text = "나중에 입력할게요")
}


@Preview
@Composable
private fun LuckyButtonPreview() {
    LuckyTheme {
        LuckyButton(text = "농작물",{})
    }
}

@Preview
@Composable
private fun LuckyDialogButtonPreview() {
    LuckyTheme {
        LuckyDialogButton(text = "농작물",{})
    }
}