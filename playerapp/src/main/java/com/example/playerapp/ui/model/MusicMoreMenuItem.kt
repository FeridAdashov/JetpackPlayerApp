package com.example.playerapp.ui.model

import androidx.annotation.DrawableRes

data class MusicMoreMenuItem(
    val title: String,
    @DrawableRes val leadingDrawable: Int,
    @DrawableRes val trailingDrawable: Int? = null,
    val trailingText: String? = null
)
