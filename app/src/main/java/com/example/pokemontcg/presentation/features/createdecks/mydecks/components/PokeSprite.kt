package com.example.pokemontcg.presentation.features.createdecks.mydecks.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun PokeSprite(
    modifier: Modifier = Modifier,
    pokeResId : Int
) {
    Image(
        painter = painterResource(id = pokeResId), // Site https://www.stickpng.com/img/download/5859611e4f6ae202fedf2859
        contentDescription = "",
        modifier = modifier
            .aspectRatio(1f)
        ,
    )
}