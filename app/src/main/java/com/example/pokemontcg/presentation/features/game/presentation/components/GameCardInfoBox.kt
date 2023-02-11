package com.example.pokemontcg.presentation.features.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.calcDominantColor

@Composable
fun GameCardInfoBox(
    modifier : Modifier = Modifier,
    imageUrl : String,
    radius : Float = 1160f
) {
    val defaultDominantColor =  Color.Transparent
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .padding(13.dp)
            .shadow(
                elevation = 3.dp,
                spotColor = Color.White
                )
            .padding(3.dp)
            .background(
                Brush.radialGradient(
                    listOf(
                        dominantColor,
                        Color.Transparent,
                    ),
                    radius = radius,
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            onSuccess = {
                isLoading = false
                calcDominantColor(it.result.drawable) { color ->
                    dominantColor = color

                }
            },

            onLoading = {
                isLoading = true
            },
            contentDescription = "",
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
                .clip(RoundedCornerShape(50.dp))
                .padding(horizontal = 10.dp),

            )
    }
}