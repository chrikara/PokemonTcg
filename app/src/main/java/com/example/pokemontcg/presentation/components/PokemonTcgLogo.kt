package com.example.pokemontcg.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokemontcg.R

@Composable
fun PokemonTcgLogo(
    modifier : Modifier = Modifier,
    size : Dp = 250.dp
) {

    Image(
        painter = painterResource(id = R.drawable.pokelogo),
        contentDescription = "pokeLogoWelcomeScreen",
        modifier = modifier
            .size(size)
            .background(MaterialTheme.colorScheme.surface)
    )
}