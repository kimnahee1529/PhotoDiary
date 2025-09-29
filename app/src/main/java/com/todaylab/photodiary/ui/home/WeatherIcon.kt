package com.todaylab.photodiary.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.todaylab.photodiary.R
import com.todaylab.photodiary.domain.model.WeatherType

@Composable
fun WeatherIcon(
    type: WeatherType,
    selectedWeather: WeatherType?,
    onSelect: (WeatherType) -> Unit
) {
    val iconRes = when (type) {
        WeatherType.SUNNY ->
            if (selectedWeather == type) R.drawable.icon_selected_sunny else R.drawable.icon_sunny
        WeatherType.CLOUDY ->
            if (selectedWeather == type) R.drawable.icon_selected_cloudy else R.drawable.icon_cloudy
        WeatherType.RAINY ->
            if (selectedWeather == type) R.drawable.icon_selected_rainy else R.drawable.icon_rainy
        WeatherType.SNOWY ->
            if (selectedWeather == type) R.drawable.icon_selected_snowy else R.drawable.icon_snowy
        // TODO : ETC일 때 수정
        WeatherType.ETC ->
            if (selectedWeather == type) R.drawable.icon_selected_sunny else R.drawable.icon_selected_sunny
    }

    Image(
        painter = painterResource(id = iconRes),
        contentDescription = type.name,
        modifier = Modifier
            .size(20.dp)
            .clickable { onSelect(type) }
    )
}