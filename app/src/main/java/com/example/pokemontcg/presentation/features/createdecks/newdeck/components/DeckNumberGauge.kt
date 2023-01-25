package com.example.pokemontcg.presentation.features.createdecks.newdeck.components

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

@Composable
fun DeckNumberGauge(
    modifier : Modifier = Modifier,
    totalCards : Int
) {

    val backgroundColor = MaterialTheme.colorScheme.onBackground
    val primaryColor = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error

    val totalCardNumberRatio = remember {
        Animatable(0f)
    }



    LaunchedEffect(key1 = totalCards) {
        totalCardNumberRatio.animateTo(
            targetValue = totalCards.toFloat()/60,
            animationSpec = tween(
                durationMillis = 2000,
            )
        )
    }

    Canvas(modifier = modifier){

        if(totalCards<60){
            val totalCardsWidth = totalCardNumberRatio.value *size.width

            drawRoundRect(
                color = backgroundColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = primaryColor,
                size = Size(
                    width = totalCardsWidth,
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