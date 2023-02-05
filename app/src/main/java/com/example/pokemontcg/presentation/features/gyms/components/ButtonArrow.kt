package com.example.pokemontcg.presentation.features.gyms.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.GestureCancellationException
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.pokemontcg.R
import com.example.pokemontcg.ui.theme.GreenButtonColor

@Composable
fun ButtonArrow(
    modifier: Modifier = Modifier,
    rotate : Float = 0f,
    onClick : () -> Unit = {}
) {

    var isPressed by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = modifier
            .alpha(if(isPressed) 0.5f else 1f)
            .graphicsLayer {
                rotationZ = rotate
                           },
        contentAlignment = Alignment.CenterStart
    ){
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            isPressed = true
                            if(!this.tryAwaitRelease()){
                                isPressed = false
                                return@detectTapGestures
                            }
                            awaitRelease()
                            onClick()
                            isPressed = false
                        }
                    )
                }
        ){

            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(size.width - size.width/4, 0f)
                lineTo(size.width, size.height/2f)
                lineTo(size.width - size.width/4, size.height)
                lineTo(0f, size.height)

            }
            drawPath(
                path = path,
                color = Color.Black,
                )
            val strokeWidth = 2f

            val path2 = Path().apply {
                moveTo(0f + strokeWidth, 0f+ strokeWidth)
                lineTo(size.width - size.width/4- strokeWidth, 0f+ strokeWidth)
                lineTo(size.width - strokeWidth, size.height/2f )
                lineTo(size.width - size.width/4  - strokeWidth, size.height- strokeWidth)
                lineTo(0f+ strokeWidth, size.height- strokeWidth)

            }
            drawPath(
                path = path2,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF66D852),
                        Color(0xFFADF38F),
                        Color(0xFF1A9E03),

                        )
                )

            )
        }
        Image(
            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
            contentDescription ="",
            colorFilter = ColorFilter.tint(Color.Black),
            modifier = Modifier
                .size(30.dp)
                .padding(1.dp)


        )
        Image(
            painter = painterResource(id = R.drawable.baseline_play_arrow_24),
            contentDescription ="",
            colorFilter = ColorFilter.tint(Color.White),
            modifier = Modifier
                .size(30.dp)
                .padding(3.dp)

        )
    }


}