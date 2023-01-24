package com.example.pokemontcg.presentation.features.createdecks.newdeck.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.R

@Composable
fun DeckNumberHeader(
    modifier : Modifier = Modifier,
    totalCards : Int
) {
    Box(
        modifier = modifier
            .clip(
                RoundedCornerShape(
                    bottomEnd = 30.dp,
                    bottomStart = 30.dp
                )
            )
            .background(MaterialTheme.colorScheme.secondary)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(20.dp)
        ){
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(60.dp))
                    .background(MaterialTheme.colorScheme.onBackground),
            ){
                Text(
                    text = "$totalCards/60",
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 15.sp,
                )

            }

            Spacer(modifier = Modifier.width(15.dp))

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(1f)

            ) {
                Text(
                    text = "Χρειάζεσαι ακόμα ${60-totalCards} κάρτες!",
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 0.05.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 15.sp

                )
                Spacer(modifier = Modifier.height(10.dp))

                DeckNumberGauge(
                    totalCards = totalCards,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))

            Image(
                painter = painterResource(id = R.drawable.krokorok),
                contentDescription = "",
                modifier = Modifier.size(70.dp),
            )
        }
        Divider(
            modifier = Modifier.align(Alignment.BottomCenter),
            thickness = 5.dp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}