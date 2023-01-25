package com.example.pokemontcg.presentation.features.createdecks.newdeck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.CardOverview

@Composable
fun CardListRow(
    image1 : String,
    image2 : String,
    onClick1 : () -> Unit,
    onClick2 : () -> Unit

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
                .background(MaterialTheme.colorScheme.surface)
                .weight(1f)
                .clickable(
                    onClick = onClick1
                )
            ,
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
                .size(285.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .weight(1f)
                .clickable(onClick = onClick2)
        )

    }
}
        

