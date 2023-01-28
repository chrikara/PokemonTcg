package com.example.pokemontcg.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val default : Dp = 0.dp,
    val spaceExtraSmall : Dp = 4.dp,
    val spaceSmall : Dp = 7.dp,
    val spaceMedium :Dp = 10.dp,
    val spaceLarge : Dp = 15.dp,
    val spaceExtraLarge : Dp = 20.dp,
    val spaceMegaLarge : Dp = 40.dp,
    val spaceMegaMegaLarge : Dp = 60.dp,

    val paddingSmall: Dp = 10.dp,
    val paddingMedium : Dp = 15.dp,
    val paddingLarge : Dp = 20.dp,
    val paddingMegaLarge : Dp = 40.dp,

    val clipSmall : Dp = 20.dp,
    val clipMedium : Dp = 40.dp,
    val clipLarge : Dp = 80.dp
)

val LocalSpacing = compositionLocalOf { Dimensions() }