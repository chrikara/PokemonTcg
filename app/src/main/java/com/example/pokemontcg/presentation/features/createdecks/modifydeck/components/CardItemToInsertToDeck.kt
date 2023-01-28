package com.example.pokemontcg.presentation.features.createdecks.modifydeck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun CardItemToInsertToDeck(
    modifier : Modifier = Modifier,
    image: String,
    totalCounts : Int,
    onShowInfo : () -> Unit,
    onAddCard : () -> Unit,
    onDeleteCard: () -> Unit,

    )
{


    Column(
        modifier = modifier
    ) {
        Image(
            painter = rememberImagePainter(
                data = image,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier
                .size(285.dp)
                .background(MaterialTheme.colorScheme.surface)
                .clickable(
                    onClick = onShowInfo
                )
            ,
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier =
                Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onDeleteCard)
                    .padding(15.dp)
            )
            Text(
                text = "$totalCounts",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 15.dp)
            )

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "" ,
                tint = MaterialTheme.colorScheme.onBackground ,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable(onClick = onAddCard)
                    .padding(15.dp)


            )

        }
        Spacer(modifier = Modifier.height(10.dp))
    }

}