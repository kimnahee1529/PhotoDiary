package com.todaylab.cleanarchtemplate.ui.widget.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.todaylab.cleanarchtemplate.ui.theme.colors

@Composable
fun LuckyItemCard(
    label: String? = "",
    value: String? = "",
    flippedCardIndex: Int? = null, // null이 아니면 앞면
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    onDismiss: () -> Unit = {},
    onClick: (() -> Unit)? = null
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(10.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() }, // 클릭 가능하게
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (flippedCardIndex != null) MaterialTheme.colors.primary else MaterialTheme.colors.primary9),
//                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LuckyResultContent(flippedCardIndex = flippedCardIndex, label = label, value = value)
        }
    }
}