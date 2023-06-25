package com.example.playerapp.utils

import androidx.compose.ui.graphics.Brush
import com.example.playerapp.ui.theme.AppBarGradientEnd
import com.example.playerapp.ui.theme.AppBarGradientMiddle
import com.example.playerapp.ui.theme.AppBarGradientStart
import com.example.playerapp.ui.theme.BlueButtonGradientEnd
import com.example.playerapp.ui.theme.BlueButtonGradientStart
import com.example.playerapp.ui.theme.IconGradientEnd
import com.example.playerapp.ui.theme.IconGradientStart
import com.example.playerapp.ui.theme.ImageGradientBorderStart
import com.example.playerapp.ui.theme.ImageGradientEnd
import com.example.playerapp.ui.theme.ImageGradientStart
import com.example.playerapp.ui.theme.ScreenBackGradientEnd
import com.example.playerapp.ui.theme.ScreenBackGradientMiddle
import com.example.playerapp.ui.theme.ScreenBackGradientStart

val screenBackGradient = Brush.verticalGradient(
    colors = listOf(ScreenBackGradientStart, ScreenBackGradientMiddle, ScreenBackGradientEnd)
)

val appBarGradient = Brush.verticalGradient(
    colors = listOf(AppBarGradientStart, AppBarGradientMiddle, AppBarGradientEnd)
)

val imageComponentGradient = Brush.verticalGradient(
    colors = listOf(ImageGradientStart, ImageGradientEnd)
)

val imageComponentBorderGradient = Brush.verticalGradient(
    colors = listOf(ImageGradientBorderStart, ImageGradientEnd)
)

val IconGradient = Brush.verticalGradient(
    colors = listOf(IconGradientStart, IconGradientEnd)
)

val BlueButtonGradient = Brush.verticalGradient(
    colors = listOf(BlueButtonGradientStart, BlueButtonGradientEnd)
)