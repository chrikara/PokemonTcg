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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.ui.theme.BlueAlpha
import com.example.pokemontcg.ui.theme.BlueAlpha30
import com.example.pokemontcg.util.myClickable

@Composable
fun GymBox(
    modifier : Modifier = Modifier,
    isEnabled : Boolean = true,
    onClick : () -> Unit,
    gymValue : String,
    gymName : String,
    paddingValues: PaddingValues = PaddingValues(horizontal = 25.dp, vertical = 15.dp),
    leaderUrl : String
    ) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (isEnabled) 1f else 0.3f)
            .clip(RoundedCornerShape(15.dp))
            .background(BlueAlpha30)
            .border(
                width = 1.dp,
                color = BlueAlpha,
                shape = RoundedCornerShape(15.dp)
            )
            .myClickable(
                onClick = onClick,
                enabled = isEnabled
            )
            .padding(paddingValues)

    ){

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ){
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(80.dp))
                    .background(MaterialTheme.colorScheme.onBackground),
            ){
                Text(
                    text = gymValue,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center),
                    fontSize = 20.sp
                )
            }

        Spacer(modifier = Modifier.width(15.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = gymName,
                color = MaterialTheme.colorScheme.onBackground,

            )
            Spacer(modifier = Modifier.width(2.dp))

            Text(
                text = "0 / 3",
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.alpha(0.6f)
            )
        }
            Spacer(modifier = Modifier.width(15.dp))

            Image(
                painter = rememberImagePainter(
                    data = leaderUrl,
                    builder = {
                        crossfade(true)
                    },
                ),
                contentDescription = "",
                modifier = Modifier.size(80.dp),
                colorFilter = if(!isEnabled) ColorFilter.tint(MaterialTheme.colorScheme.background) else null

            )
        }
    }
}