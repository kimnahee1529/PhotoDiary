package com.todaylab.photodiary.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.ui.theme.ExampleTheme
import com.todaylab.photodiary.ui.theme.colors

@Composable
fun CloverButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.gray10
    )
) {
    Box(
        modifier = modifier
            .clickable(
                onClick = { onClick() }
            ),
        contentAlignment = Alignment.Center
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.icon_clover),
//            contentDescription = null,
//            modifier = Modifier
//                .width(200.dp),
//        )
        Text(
            text = text,
            color = MaterialTheme.colors.white,
            style = MaterialTheme.typography.headlineLarge,
        )

    }
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
            style = MaterialTheme.typography.bodyLarge,
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
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Preview
@Composable
private fun CloverButtonPreview() {
    ExampleTheme {
        CloverButton(text = "행운 찾기", {})
    }
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