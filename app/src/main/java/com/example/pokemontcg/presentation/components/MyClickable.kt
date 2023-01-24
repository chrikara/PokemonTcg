package com.example.pokemontcg.util

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.myClickable(
    color : Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit,
    enabled : Boolean = true
) : Modifier {
    return this.clickable(
        interactionSource = MutableInteractionSource(),
        indication = rememberRipple(
            color = color
        ),
        onClick = onClick,
        enabled = enabled
    )
}