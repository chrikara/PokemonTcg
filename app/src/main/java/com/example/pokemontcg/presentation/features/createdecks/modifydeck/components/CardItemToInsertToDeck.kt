package com.example.pokemontcg.presentation.features.createdecks.modifydeck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.pokemontcg.ui.theme.BlueAlpha40
import com.example.pokemontcg.ui.theme.BlueAlpha60
import com.example.pokemontcg.ui.theme.BlueAlpha80

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
        modifier = Modifier
        .fillMaxHeight()
        .padding(vertical = 2.dp)
        .padding(horizontal = 2.dp)
        .shadow(
            elevation = 2.dp,
            shape = CutCornerShape(
                topStart = 5.dp,
                bottomEnd = 5.dp
            )
        )
        .padding(1.dp)
        .border(
            width = 1.dp,

            color = Color.Black
        )
        .background(BlueAlpha40)
    ) {
        Box(){
            Image(
                painter = rememberImagePainter(
                    data = image,
                    builder = {
                              CircularProgressIndicator(
                                  modifier = Modifier.align(Alignment.Center)
                              )

                    },
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(285.dp)
                    .padding(10.dp)
                    .background(Color.Transparent)
                    .padding(horizontal = 10.dp)
                    .clickable(
                        onClick = onShowInfo
                    ),

                )
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier =
                Modifier
                    .clip(CircleShape)
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
                    .clip(CircleShape)
                    .clickable(onClick = onAddCard)
                    .padding(15.dp)


            )

        }
    }

}