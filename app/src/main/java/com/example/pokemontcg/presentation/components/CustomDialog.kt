package com.example.pokemontcg.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun CustomDialog(
    modifier : Modifier = Modifier,
    onDismiss : () -> Unit,
    backgroundColor : Color = BlueAlpha80,
    borderColor : Color = Color.Transparent,
    onClickOk : () -> Unit,
    title : String,
    text : String,
) {
    
    val txtFieldError = remember { mutableStateOf("") }
    val txtField = remember { mutableStateOf("") }
    val spacing = LocalSpacing.current

    Dialog(onDismissRequest = { onDismiss()}) {
        Box(


            contentAlignment = Alignment.Center)
        {
            
            Column(   modifier = modifier
                .border(
                    width = 0.6.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(3.dp)
                )
                .clip(RoundedCornerShape(5.dp))
                .background(backgroundColor)
                .padding(horizontal = 20.dp)
                .padding(top = 15.dp)
                ,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))

                    Text(
                        text = text,
                        letterSpacing = 0.1.sp,
                        lineHeight = 18.sp
                    )
                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ){
                    Text(
                        text = "Ακύρωση",
                        modifier =
                        Modifier
                            .clip(CircleShape)
                            .clickable { onDismiss() }
                            .padding(20.dp)

                    )
                    Text(
                        text = "OK",
                        modifier = Modifier
                            .clip(CircleShape)
                            .clickable {
                                onClickOk()
                                onDismiss()
                            }
                            .padding(20.dp)
                    )
                }
            }
            
        }
    }
}