@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.example.pokemontcg.presentation.features.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.components.GameActionWindow
import com.example.pokemontcg.presentation.features.game.components.GameCardInHandBox
import com.example.pokemontcg.presentation.features.game.components.GameChooseActive
import com.example.pokemontcg.presentation.features.game.components.GameHandBox
import com.example.pokemontcg.presentation.features.game.components.ShuffleDeckGame
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.UiEvent
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    snackbarHostState : SnackbarHostState
) {
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
                else -> {}
            }
        }
    }

    val state = viewModel.state

    if(state.currentState == GameState.GameSealedClass.LOADING){
        LoadingScreen()
    }

    if(state.currentState == GameState.GameSealedClass.START){
        ShuffleDeckGame(secondsToStart = 4, viewModel = viewModel)
    }

    if(state.currentState in GAMESTATE_CHOOSE_ACTIVE_LIST){
        GameChooseActive(viewModel = viewModel,)
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN){
        GameMainScreen(nationalDex = 2)
    }





}




@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Center)
        )
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GameMainScreen(
    modifier : Modifier = Modifier,
    nationalDex : Int
){
    val listState = rememberLazyListState()


    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
        reverseLayout = true
    ){
        item {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = rememberImagePainter(
                        data =
                        when(nationalDex.toString().length){
                            1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00${nationalDex}.png"
                            2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0${nationalDex}.png"
                            else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${nationalDex}.png"
                        }
                    ),
                    contentDescription ="",
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.BottomStart)
                        .offset(y = -100.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueAlpha80)
                        .border(width = 1.dp, color = Color.Black)
                        .padding(15.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    maxItemsInEachRow = 2
                ) {
                    val paddingValues = PaddingValues(15.dp)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Attack", paddingValues = paddingValues, isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Hand", paddingValues = paddingValues,isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Check", paddingValues = paddingValues,isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Pass", paddingValues = paddingValues,isEnabled = true)

                }
            }


            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp))

            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = rememberImagePainter(
                        data =
                        when((nationalDex+3).toString().length){
                            1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00${nationalDex+3}.png"
                            2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0${nationalDex+3}.png"
                            else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${nationalDex+3}.png"
                        }
                    ),
                    contentDescription ="",
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}