package com.bitaam.iconfinderappandroid.ui.screens.icons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bitaam.iconfinderappandroid.data.models.Icon
import com.bitaam.iconfinderappandroid.ui.comps.LoadImage

@Composable
fun IconsView(icon: Icon, onIconClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                onIconClick()
            }
            .padding(4.dp)
    ) {
        LoadImage(
            icon.raster_sizes.first().formats.first().preview_url,
            modifier = Modifier.fillMaxWidth().height(200.dp),
        )
    }
}