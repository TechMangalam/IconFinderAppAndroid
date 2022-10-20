package com.bitaam.iconfinderappandroid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Icon(
    val icon_id: Int,
    val raster_sizes: List<RasterSize>,
    val is_premium: Boolean,
    val type: String,
) : Parcelable
