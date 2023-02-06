package com.example.pokemontcg.presentation.features.gyms

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.defaultPewterOpponents
import com.example.pokemontcg.domain.model.toInt
import com.example.pokemontcg.domain.model.toNumberString
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.gyms.components.ButtonArrow
import com.example.pokemontcg.presentation.features.gyms.components.GymOpponentBox
import com.example.pokemontcg.ui.theme.BlueAlpha40
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.UiEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun GymScreen(
    snackbarHostState : SnackbarHostState,
    viewModel: GymViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current
    val state = viewModel.state
    println(state)

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.ShowSnackBar ->{

                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
                else -> Unit
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BlueAlpha40)
            .padding(horizontal = spacing.paddingSmall),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween

    ){
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        Column(modifier = Modifier){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        CutCornerShape(
                            topStart = spacing.spaceMedium,
                            topEnd = spacing.spaceMedium
                        )
                    )
                    .background(BlueAlpha80)
                    .padding(spacing.paddingSmall)
            ) {
                Text(
                    text = "Διάλεξε αντίπαλο",
                    fontWeight = FontWeight.Bold,
                    fontSize =  20.sp,
                    style = MaterialTheme.typography.displaySmall.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(x =5f, y = 7f),
                            blurRadius = 10f,
                        )
                    )

                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(200.dp)
                    .background(Color(0x3307121F))
                    .drawBehind {
                        val strokeWidth = 1f * density
                        val y = size.height - strokeWidth / 2
                        val x = size.width - strokeWidth / 2

                        drawLine(
                            BlueAlpha80,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                        drawLine(
                            BlueAlpha80,
                            Offset(strokeWidth / 2, 0f),
                            Offset(strokeWidth / 2, y),
                            strokeWidth
                        )

                        drawLine(
                            BlueAlpha80,
                            Offset(size.width - strokeWidth / 2, y),
                            Offset(size.width - strokeWidth / 2, 0f),
                            strokeWidth
                        )
                    }
                    .padding(horizontal = spacing.paddingSmall),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = state.selectedOpponent,
                    transitionSpec = {
                        if(defaultPewterOpponents.indexOf(targetState) > defaultPewterOpponents.indexOf(initialState) ){
                            slideInHorizontally( initialOffsetX = {
                                it
                            }) with slideOutHorizontally(targetOffsetX = {
                                -it
                            })
                        } else {
                            slideInHorizontally( initialOffsetX = {
                                -it
                            }) with slideOutHorizontally(targetOffsetX = {
                                it
                            })
                        }
                    }
                ) {

                    GymOpponentBox(
                        modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(horizontal = 50.dp, vertical = 20.dp),
                        opponent = it!!
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    ButtonArrow(

                        modifier = Modifier.size(
                            width = 30.dp,
                            height = 40.dp
                        ),
                        onClick = {viewModel.onEvent(GymEvent.OnPreviousOpponent)},
                        rotate = 180f,
                        isEnabled = state.isPreviousOpponentEnabled
                    )


                    ButtonArrow(
                        modifier = Modifier
                            .size(
                                width = 30.dp,
                                height = 40.dp
                            ),
                        onClick = {viewModel.onEvent(GymEvent.OnNextOpponent)},
                        isEnabled = state.isNextOpponentEnabled
                    )
                }



            }

        }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))
        Column(modifier = Modifier.weight(1f)){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        CutCornerShape(
                            topStart = spacing.spaceMedium,
                            topEnd = spacing.spaceMedium
                        )
                    )
                    .background(BlueAlpha80)
                    .padding(spacing.paddingSmall)
            ) {
                Text(
                    text = "Διάλεξε τράπουλα",
                    fontWeight = FontWeight.Bold,
                    fontSize =  20.sp,
                    style = MaterialTheme.typography.displaySmall.copy(
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(x =5f, y = 7f),
                            blurRadius = 10f,
                        )
                    )

                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(300.dp)
                    .background(Color(0x3307121F))
                    .drawBehind {
                        val strokeWidth = 1f * density
                        val y = size.height - strokeWidth / 2
                        val x = size.width - strokeWidth / 2

                        drawLine(
                            BlueAlpha80,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                        drawLine(
                            BlueAlpha80,
                            Offset(strokeWidth / 2, 0f),
                            Offset(strokeWidth / 2, y),
                            strokeWidth
                        )

                        drawLine(
                            BlueAlpha80,
                            Offset(size.width - strokeWidth / 2, y),
                            Offset(size.width - strokeWidth / 2, 0f),
                            strokeWidth
                        )
                    }
                    .padding(horizontal = spacing.paddingSmall),
                contentAlignment = Alignment.Center
            ) {

                LazyRow(
                    Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    items(defaultDecks){ deck ->
                        CardBox(
                            isSelected = state.selectedDeck == deck,
                            deckNumber = deck,
                            onClick = {
                                viewModel.onEvent(GymEvent.OnDeckClicked(deckNumber = deck))
                            }
                        )

                    }

                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ){
                    ButtonArrow(

                        modifier = Modifier.size(
                            width = 30.dp,
                            height = 40.dp
                        ),
                        onClick = {viewModel.onEvent(GymEvent.OnPreviousDeck)},
                        rotate = 180f,
                        isEnabled = state.isPreviousDeckEnabled
                    )


                    ButtonArrow(
                        modifier = Modifier
                            .size(
                                width = 30.dp,
                                height = 40.dp
                            ),
                        onClick = {
                            viewModel.onEvent(GymEvent.OnNextDeck)
                                  },
                        isEnabled = state.isNextDeckEnabled
                    )
                }

            }

            }
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

        ButtonSecondary(
            text = "Παίξε!",
            fontSize = 25.sp,
            onClick = { viewModel.onEvent(GymEvent.OnPlay) }
        )
        Spacer(modifier = Modifier.height(spacing.spaceMedium))

    }




    }
@Composable
private fun CardBox(
    isSelected : Boolean,
    deckNumber: DeckNumber,
    onClick : (DeckNumber) -> Unit
){
    val borderWidth = 0.7.dp
    Column(
        modifier = Modifier
            .alpha(if (isSelected) 1f else 0.3f)
            .fillMaxHeight()
            .padding(vertical = 15.dp)
            .padding(horizontal = 20.dp)
            .clickable { onClick(deckNumber) }
            .shadow(
                elevation = 2.dp,
                shape = CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp

                )
            )
            .padding(1.dp)
            .clip(
                CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp
                )
            )
            .border(
                width = borderWidth,
                shape = CutCornerShape(
                    topStart = 5.dp,
                    bottomEnd = 5.dp

                ),
                color = Color.Black
            )
            .background(BlueAlpha80)
            ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "${deckNumber.toNumberString()} Deck",
            style = MaterialTheme.typography.displaySmall.copy(
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(x =5f, y = 7f),
                    blurRadius = 10f,
                )
            ),
            fontSize = 15.sp,
            modifier = Modifier
                .padding(vertical = 5.dp)

        )
        Image(
            painter = rememberImagePainter(
                data = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4f7705ec-8c49-4eed-a56e-c21f3985254c/dah43cy-a8e121cb-934a-40f6-97c7-fa2d77130dd5.png/v1/fill/w_1024,h_1420,strp/pokemon_card_backside_in_high_resolution_by_atomicmonkeytcg_dah43cy-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQyMCIsInBhdGgiOiJcL2ZcLzRmNzcwNWVjLThjNDktNGVlZC1hNTZlLWMyMWYzOTg1MjU0Y1wvZGFoNDNjeS1hOGUxMjFjYi05MzRhLTQwZjYtOTdjNy1mYTJkNzcxMzBkZDUucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.9GzaYS7sd8RPY5FlHca09J9ZQZ9D9zI69Ru-BsbkLDA"
                ,
                builder = {
                    crossfade(true)
                },
            ),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .padding(bottom = 4.dp)
                .clip(CutCornerShape(bottomEnd = 5.dp))
                .border(
                    color = Color.Black,
                    width = borderWidth,
                    shape = CutCornerShape(bottomEnd = 5.dp)
                )
                .width(160.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFFE69F49),
                            Color(0xFFE69F49),
                            Color(0xFFFFFB00)

                        ),
                        startY = 0f,
                        endY = 400f
                    )
                )
                .fillMaxHeight(1f)
                .padding(vertical = 10.dp)
                .weight(1f)

            )
    }
}

val defaultDecks = listOf<DeckNumber>(
    DeckNumber.First,
    DeckNumber.Second,
    DeckNumber.Third,
)
