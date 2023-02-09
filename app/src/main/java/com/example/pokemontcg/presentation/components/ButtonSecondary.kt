package com.example.pokemontcg.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.clearCompositionErrors
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.R
import com.example.pokemontcg.ui.theme.GreenBrush
import com.example.pokemontcg.ui.theme.GreenButtonColor
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun ButtonSecondary(
    modifier : Modifier = Modifier,
    text : String,
    onClick : () -> Unit = {},
    fontSize : TextUnit = 20.sp,
    paddingValues: PaddingValues = PaddingValues(
        horizontal = 55.dp,
        vertical = 15.dp
    ),
    isEnabled : Boolean,
) {

    val spacing = LocalSpacing.current


    Box(
        modifier = modifier

            .shadow(
                elevation = 4.dp,
                shape = CutCornerShape(
                    topStart = spacing.clipExtraSmall,
                    bottomEnd = spacing.clipExtraSmall
                )
                )
            .padding(2.dp)
            .clip(
                CutCornerShape(
                    topStart = spacing.clipExtraSmall,
                    bottomEnd = spacing.clipExtraSmall
                )
            )
            .clickable(
                enabled = isEnabled,
                onClick = onClick
            )
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.background,
                shape = CutCornerShape(
                    topStart = spacing.clipExtraSmall,
                    bottomEnd = spacing.clipExtraSmall
                )
            )
            .alpha(if(isEnabled) 1f else 0.4f)
            .background(
                brush = Brush.verticalGradient(
                    colors = GreenBrush,
                    startY = 10f,
                    endY = 150f

                )
            )
            .padding(paddingValues)


    ){
        Text(
            text = text,
            fontWeight = FontWeight.ExtraBold,
            fontSize =  fontSize,
            style = MaterialTheme.typography.displaySmall.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(x =5f, y = 7f),
                    blurRadius = 10f,
                )
            )
        ,

        )
    }
}