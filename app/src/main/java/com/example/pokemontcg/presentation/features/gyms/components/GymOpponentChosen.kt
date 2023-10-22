package com.example.pokemontcg.presentation.features.gyms.components

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.R
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.calcDominantColor
import com.example.pokemontcg.ui.theme.BlueAlpha
import com.example.pokemontcg.ui.theme.BlueAlpha60
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun GymOpponentBox(
    modifier : Modifier = Modifier,
    opponent: GymOpponent,
    normalTrainersBeaten : Int = 0

) {


    val spacing = LocalSpacing.current
    val context = LocalContext.current
    var dominantColor by remember {
        mutableStateOf(Color.Transparent)
    }
    calcDominantColor(drawable = opponent.image, context = context){
        dominantColor = it
    }


    Box(
        modifier =modifier
    ){
        Canvas(
            modifier = Modifier.fillMaxSize()
        ){
            val width = size.width
            val height = size.height
            val num = 5

            val pathStroke = Path().apply {
                moveTo(0f, 0f)
                lineTo(width - width/num, 0f)
                lineTo(width, height/2f)
                lineTo(width - width/num, height)
                lineTo(0f, height)
                lineTo(width/num , height/2f)
                lineTo(0f, 0f)
                close()
            }

            drawPath(
                path = pathStroke,
                color = Color.Black,
                style = Stroke(
                    width = 2f,
                )
            )


            val path = Path().apply {
                moveTo(0f, 0f)
                lineTo(width - width/num, 0f)
                lineTo(width, height/2f)
                lineTo(width - width/num, height)
                lineTo(0f, height)
                lineTo(width/num , height/2f)
                lineTo(0f, 0f)
            }

            drawPath(
                path = path,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        dominantColor,
                        Color.Transparent

                    ),
                    startX = 0f,
                    endX = 1100f
                )
            )

        }
        Image(
            painter = painterResource(id = opponent.image),
            contentDescription = "",
            modifier = Modifier
                .aspectRatio(1f)
                .offset(x = -30.dp)
        )

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(start = 20.dp)
                .padding(top = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                text = opponent.name,
                style = MaterialTheme.typography.displaySmall.copy(
                    shadow = Shadow(
                        color = Color.Black,
                        offset = Offset(x =5f, y = 7f),
                        blurRadius = 10f,
                    )
                )
            )

            Image(
                modifier = Modifier.size(30.dp),
                painter = painterResource(id = opponent.symbolImage),
                contentDescription = ""
            )




        }
        val borderRadius = 20.dp

        if(opponent.isBoss){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 1.dp)
                    .align(Alignment.BottomCenter)
                    .padding(end = 40.dp)

                    .clip(
                        RoundedCornerShape(
                            topEnd = borderRadius,
                            bottomEnd = borderRadius
                        )
                    )
                    .background(
                        brush = Brush.horizontalGradient(
                            startX = 700f,
                            endX = 0f,

                            colors = listOf(
                                Color(0xCC1F81E9),
                                Color.Transparent
                            )
                        ),
                    )
                    .padding(5.dp)
                ,
                horizontalArrangement = Arrangement.End
            ){


                Image(
                    painter =
                    if (normalTrainersBeaten>=1) painterResource(id = R.drawable.star_filled)
                    else painterResource(id = R.drawable.star),
                    contentDescription = "",
                    modifier = Modifier.size(30.dp),
                    colorFilter = ColorFilter.tint(Color.Black)
                )
                Image(
                    painter =
                    if (normalTrainersBeaten>=2) painterResource(id = R.drawable.star_filled)
                    else painterResource(id = R.drawable.star),

                    contentDescription = "",
                    modifier = Modifier.size(30.dp),
                )
            }
        }

    }

}