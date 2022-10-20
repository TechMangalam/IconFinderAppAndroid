package com.bitaam.iconfinderappandroid.data.models

import com.bitaam.iconfinderappandroid.data.models.Category

data class CategoryResponse(
    val categories: List<Category>,
    val total_count: Int,
)
