package com.bitaam.iconfinderappandroid.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Format(
    val format: String,
    val download_url: String,
    val preview_url: String,
) : Parcelable