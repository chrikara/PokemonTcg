package com.example.pokemontcg.presentation.features.gyms

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.gyms.components.ButtonArrow
import com.example.pokemontcg.ui.theme.BlueAlpha
import com.example.pokemontcg.ui.theme.BlueAlpha20
import com.example.pokemontcg.ui.theme.BlueAlpha40
import com.example.pokemontcg.ui.theme.BlueAlpha60
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun GymScreen(

) {

    val spacing = LocalSpacing.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(spacing.paddingExtraExtraSmall)
            .clip(RoundedCornerShape(spacing.clipExtraSmall))
            .background(BlueAlpha40)
            .border(
                width = 2.dp,
                color = BlueAlpha,
                shape = RoundedCornerShape(spacing.clipExtraSmall)
            )

            .padding(spacing.paddingSmall),
        horizontalAlignment = Alignment.CenterHorizontally

    ){

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    CutCornerShape(
                        topStart = spacing.spaceMedium,
                        topEnd = spacing.spaceMedium
                    )
                )
                .background(BlueAlpha80)
                .padding(spacing.paddingSmall)



        ) {
            Text(
                text = "Select opponent",
                fontWeight = FontWeight.Bold

            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier

                .fillMaxWidth()

                .size(200.dp)
                .background(Color(0x3307121F))
                .drawBehind {
                    val strokeWidth = 1f * density
                    val y = size.height - strokeWidth / 2
                    val x = size.width - strokeWidth / 2

                    drawLine(
                        BlueAlpha80,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                    drawLine(
                        BlueAlpha80,
                        Offset(strokeWidth / 2, 0f),
                        Offset(strokeWidth / 2, y),
                        strokeWidth
                    )

                    drawLine(
                        BlueAlpha80,
                        Offset(size.width - strokeWidth / 2, y),
                        Offset(size.width - strokeWidth / 2, 0f),
                        strokeWidth
                    )
                }
                .padding(horizontal = spacing.paddingSmall)


        ) {

            ButtonArrow(

                modifier = Modifier.size(
                    width = 30.dp,
                    height = 40.dp
                ),
                rotate = 180f
            )
            ButtonArrow(
                modifier = Modifier
                    .size(
                        width = 30.dp,
                        height = 40.dp
                    )
            )

        }

        Spacer(modifier = Modifier.height(spacing.spaceMegaMegaLarge))

        ButtonSecondary(
            modifier = Modifier,
            text = "Play!",
            fontSize = 25.sp
        )
    }
}