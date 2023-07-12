package com.example.playerapp.ui.model

import androidx.annotation.DrawableRes

data class MusicCategory(
    val categoryType: MusicCategoryType,
    val title: String,
    @DrawableRes val imageDrawable: Int
)
