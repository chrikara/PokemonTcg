package com.example.pokemontcg.presentation.features.createdecks.modifydeck.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemontcg.R
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.SearchTextField
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL

@Composable
fun DeckNumberHeader(
    modifier : Modifier = Modifier,
    totalCards : Int,
    onFinishedRatio : (Float) -> Unit = {},
    initialRatio : Float = 0.0f,

    hasSearchBar : Boolean = false,
    isSearchBarExpanded : Boolean = false,
    onSearchIconClicked : () -> Unit = {},
    onSearchBarClicked : () -> Unit = {},
    onBackPressed : () -> Unit = {},

    query : String = "",
    onValueChange : (String) -> Unit = {},
    onFocusChange : (FocusState) -> Unit = {},

    isHintVisible : Boolean = true,
    isHeaderVisible : Boolean = true


    ) {
    val spacing = LocalSpacing.current


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
        Column {

            AnimatedVisibility(visible = isHeaderVisible) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(horizontal = spacing.paddingMedium, vertical = spacing.paddingSmall)
                ){
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(60.dp))
                            .background(MaterialTheme.colorScheme.onBackground),
                    ){
                        Text(
                            text = "$totalCards/$TOTAL_DECK_CARDS_GLOBAL",
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
                            text = when{
                                totalCards < TOTAL_DECK_CARDS_GLOBAL - 1 -> {"Χρειάζεσαι ${TOTAL_DECK_CARDS_GLOBAL-totalCards} κάρτες!"}
                                totalCards == TOTAL_DECK_CARDS_GLOBAL - 1 -> {"Χρειάζεσαι 1 κάρτα!"}
                                else -> {"Πλήρης τράπουλα, παίξε!"}
                            }
                            ,
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
                                .height(20.dp),
                            onFinishedRatio = {
                                onFinishedRatio(it)
                            },
                            initialRatio = initialRatio
                        )
                    }
                    Spacer(modifier = Modifier.width(15.dp))

                    if(hasSearchBar){
                        Image(
                            imageVector = Icons.Default.Search
                            ,
                            contentDescription = "",
                            modifier = Modifier
                                .size(70.dp)
                                .clip(
                                    RoundedCornerShape(spacing.clipLarge)
                                )
                                .clickable { onSearchIconClicked() }
                                .background(if (isSearchBarExpanded) Color(0x26FFFFFF) else Color.Transparent)
                                .padding(spacing.paddingMedium)

                            ,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.krokorok)
                            ,
                            contentDescription = "",
                            modifier = Modifier
                                .size(70.dp)
                            ,
                        )
                    }

                }

            }

            AnimatedVisibility(visible = isSearchBarExpanded){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.height(IntrinsicSize.Max)
                ){

                    AnimatedVisibility(
                        visible = !isHeaderVisible,
                        enter = fadeIn() + expandHorizontally(
                            animationSpec =  tween(durationMillis = 500)
                        ),
                        exit = fadeOut() + shrinkHorizontally(
                            animationSpec =  tween(durationMillis = 500)
                        )
                    ) {
                        Image(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier
                                .padding(start = 15.dp)
                                .clip(
                                    RoundedCornerShape(30.dp)
                                )
                                .clickable(onClick = onBackPressed)
                                .padding(15.dp)
                                .size(30.dp)
                                .fillMaxHeight()
                                ,
                            colorFilter = ColorFilter.tint(
                                color = MaterialTheme.colorScheme.onBackground
                            )

                        )
                    }


                    SearchTextField(
                        modifier = Modifier
                            .padding(horizontal = 25.dp)
                        ,
                        query = query,
                        onValueChange = onValueChange,
                        onFocusChange = {
                            onFocusChange(it)
                            onSearchBarClicked()
                        },
                        isHintVisible = isHintVisible
                    )
                }

            }
            Divider(
                modifier = Modifier,
                thickness = 5.dp,
                color = MaterialTheme.colorScheme.primary
            )
        }

    }
}



