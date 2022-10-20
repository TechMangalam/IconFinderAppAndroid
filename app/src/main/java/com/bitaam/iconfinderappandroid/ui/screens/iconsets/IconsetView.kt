package com.bitaam.iconfinderappandroid.ui.screens.iconsets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitaam.iconfinderappandroid.data.models.Iconset

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IconsetView(iconset: Iconset, onIconsetClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onIconsetClick() }
            .padding(4.dp)
    ) {
        Text(text = iconset.name, modifier = Modifier.padding(4.dp))
    }
}