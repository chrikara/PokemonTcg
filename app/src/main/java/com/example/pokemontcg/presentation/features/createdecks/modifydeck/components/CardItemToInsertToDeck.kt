package com.example.pokemontcg.presentation.features.createdecks.modifydeck.components

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.pokemontcg.ui.theme.BlueAlpha40
import com.example.pokemontcg.ui.theme.BlueAlpha60
import com.example.pokemontcg.ui.theme.BlueAlpha80

@Composable
fun CardItemToInsertToDeck(
    image: String,
    totalCounts : Int,
    onShowInfo : () -> Unit,
    onAddCard : () -> Unit = {},
    onDeleteCard: () -> Unit = {},

    )
{
    val defaultDominantColor =  Color.Transparent
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    var isLoading by remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(vertical = 2.dp)
            .padding(horizontal = 2.dp)
            .shadow(
                elevation = 3.dp,

            )
            .padding(3.dp)
            .background(
                Brush.radialGradient(
                    listOf(
                        dominantColor,
                        Color.Transparent,
                    ),
                    radius = 760f,
                )
            )
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                onSuccess = {
                    isLoading = false
                    calcDominantColor(it.result.drawable) { color ->
                        dominantColor = color

                    }
                },

                onLoading = {
                    isLoading = true
                },
                contentScale = ContentScale.Fit,
                contentDescription = "",
                modifier = Modifier
                    .size(285.dp)
                    .clickable(
                        onClick = onShowInfo
                    )
                    .clip(RoundedCornerShape(50.dp))
                    .padding(horizontal = 10.dp),

                )
            if(isLoading){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
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

fun calcDominantColor(drawable: Drawable, onFinish:(Color) -> Unit){
    val bmp = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)

    Palette.from(bmp).generate{
        it?.dominantSwatch?.rgb?.let { colorValue ->
            onFinish(Color(colorValue))
        }
    }
}