package com.bitaam.iconfinderappandroid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Iconset(
    val name: String,
    val identifier: String,
    val type: String,
    val iconset_id: Int,
    val is_premium: Boolean,
    val icons_count: Int,
) : Parcelable
