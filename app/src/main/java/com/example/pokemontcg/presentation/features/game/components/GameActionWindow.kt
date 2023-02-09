package com.example.pokemontcg.presentation.features.game.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.GreenBrush

@Composable
fun GameActionWindow(
    title : String,
    message : String,
    borderTitleColor : Color = BlueAlpha80,
    borderMessageColor : List<Color> = GreenBrush,
    onClick : () -> Unit

) {
    var isClicked by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(isClicked) {
                // Ensures that it was clicked once

                if(isClicked){
                    return@pointerInput
                }
                detectTapGestures {
                    isClicked = true
                    onClick()
                }
            }
    ){
        Text(
            modifier = Modifier

                .padding(3.dp)
                .border(
                    width = 5.dp,
                    color = borderTitleColor,
                    shape = RoundedCornerShape(5.dp)
                )
                .padding(15.dp)
                .align(Alignment.Center),
            letterSpacing = 7.sp,
            text = title,
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )

        Box(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(5.dp))
        {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 5.dp,
                        brush = Brush.verticalGradient(
                            borderMessageColor
                        ),
                        shape = RoundedCornerShape(5.dp)
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .padding(15.dp)
                    .padding(end = 55.dp)
                    .align(Alignment.Center)
                ,
                text = message,
                textAlign = TextAlign.Start,
                color = Color.White,
                fontSize = 16.sp
            )
            Text(
                text = "Tap",
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.CenterEnd),
                color = Color.Red,
            )
        }

    }
}