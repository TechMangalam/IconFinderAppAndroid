package com.bitaam.iconfinderappandroid.data.models

import com.bitaam.iconfinderappandroid.data.models.Iconset

data class IconsetResponse(
    val iconsets: List<Iconset>,
    val total_count: Int,
)
