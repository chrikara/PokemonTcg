package com.example.pokemontcg.presentation.features.createdecks.modifydeck.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL

@Composable
fun DeckNumberGauge(
    modifier : Modifier = Modifier,
    totalCards : Int,
    initialRatio : Float = 0f,
    onFinishedRatio : (Float) -> Unit = {}
) {


    val backgroundColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error


    val totalCardNumberRatio = remember {
        Animatable( initialValue = initialRatio)
    }
        LaunchedEffect(key1 = totalCards) {
            totalCardNumberRatio.animateTo(
                targetValue = totalCards.toFloat()/ TOTAL_DECK_CARDS_GLOBAL,
                animationSpec = tween(
                    durationMillis = 2000,
                )
            )
            onFinishedRatio(totalCardNumberRatio.value)
        }





    Canvas(modifier = modifier){

        if(totalCards<TOTAL_DECK_CARDS_GLOBAL){

            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = primaryColor,
                size = Size(
                    width = totalCardNumberRatio.value*size.width,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)

            )
        }else{
            drawRoundRect(
                color = errorColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }

    }

}