package com.example.pokemontcg.presentation.features.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.ui.theme.BlueAlpha20

@Composable
fun InfoBox(
    title : String,
    description : String,
    modifier : Modifier = Modifier,
    imageUrl : String = "",
    isEnabled : Boolean = true,
    onClick : () -> Unit,
    paddingValues: PaddingValues = PaddingValues(horizontal = 25.dp, vertical = 15.dp),
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .background(BlueAlpha20)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(15.dp)
            )
            .clickable(onClick = onClick)
            .padding(vertical = 20.dp)


    ){
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    },
                ),
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(100.dp)

                ,
                colorFilter = if(!isEnabled) ColorFilter.tint(MaterialTheme.colorScheme.background) else null
            )

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(end = 10.dp)
            ) {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = description,
                    color = MaterialTheme.colorScheme.onBackground,

                )

                Spacer(modifier = Modifier.height(40.dp))


            }

        }

    }
}

