package com.todaylab.cleanarchtemplate.ui.widget.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.todaylab.cleanarchtemplate.ui.theme.LuckyTheme
import com.todaylab.cleanarchtemplate.ui.widget.LuckyButton

@Composable
fun ConfirmButtonDialog(
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
                LuckyButton(text = "확인", onClick = onConfirm)
            }
        }
    }
}

@Preview
@Composable
private fun PreviewConfirmButtonDialog() {
    LuckyTheme {
        ConfirmButtonDialog(
            onDismissRequest = {},
            onConfirm = {},
            content = {
                Text("Dialog Content")
            },
        )
    }
}