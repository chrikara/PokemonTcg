package com.example.pokemontcg.presentation.features.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokemontcg.ui.theme.BlueAlpha
import com.example.pokemontcg.ui.theme.BlueAlpha30

@Composable
fun PrimaryButton(
    text : String,
    modifier: Modifier = Modifier,
    onClick : () -> Unit,
    paddingValues: PaddingValues = PaddingValues(horizontal = 45.dp, vertical = 15.dp),
    enabled : Boolean = true,
    textAlign: TextAlign? = TextAlign.Center
) {

    Box(
        modifier = modifier
            .alpha(if(enabled) 1f else 0.4f)
            .clip(RoundedCornerShape(25.dp))
            .background(BlueAlpha30)
            .border(
                width = 1.dp,
                color = BlueAlpha,
                shape = RoundedCornerShape(25.dp)
            )
            .clickable(
                onClick = onClick,
                enabled = enabled
            )
            .padding(paddingValues)

    ){
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = text,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = textAlign
        )
    }
}