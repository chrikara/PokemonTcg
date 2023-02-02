package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SearchTextField(
    modifier : Modifier = Modifier,
    query : String,
    onValueChange: (String) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    isHintVisible : Boolean

) {

    Box(
        modifier = modifier
    ) {

        BasicTextField(
            value = query,
            onValueChange = onValueChange,
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .padding(3.dp)
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(20.dp),
                    spotColor = Color.White,
                    ambientColor = Color.White
                )
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 20.dp, vertical = 10.dp)
                .onFocusChanged { onFocusChange(it) }
            ,

            singleLine = true
        )

        if(isHintVisible){
            Text(
                text = "Search...",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Light,
                color = Color.LightGray,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 20.dp)
            )
        }


    }

}