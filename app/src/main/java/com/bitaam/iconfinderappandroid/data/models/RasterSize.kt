package com.bitaam.iconfinderappandroid.data.models

import android.os.Parcelable
import com.bitaam.iconfinderappandroid.data.models.Format
import kotlinx.parcelize.Parcelize

@Parcelize
data class RasterSize(
    val size_height: Int,
    val size: Int,
    val size_width: Int,
    val formats: List<Format>,
) : Parcelable
