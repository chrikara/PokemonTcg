package com.example.pokemontcg.presentation.carddetails.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CardListRow(
    image1 : String,
    image2 : String,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ){

        Image(
            painter = rememberImagePainter(
                data = image1,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(285.dp)
                .background(Color.Transparent)
                .weight(1f)
        )

                Image(
                    painter = rememberImagePainter(
                        data = image2,
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .weight(1f)
                )
            }


        }
        

