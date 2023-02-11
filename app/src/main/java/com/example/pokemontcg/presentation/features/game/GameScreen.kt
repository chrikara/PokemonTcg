@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.example.pokemontcg.presentation.features.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemontcg.presentation.features.game.presentation.components.GameChooseActiveScreen
import com.example.pokemontcg.presentation.features.game.presentation.components.GameChooseBenchScreen
import com.example.pokemontcg.presentation.features.game.components.GamePlayerTurnScreen
import com.example.pokemontcg.presentation.features.game.presentation.components.ShuffleDeckGameScreen
import com.example.pokemontcg.util.UiEvent

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
    println("STATE MAIN ${viewModel.state.currentState}")

    if(state.currentState == GameState.GameSealedClass.LOADING){
        LoadingScreen()
    }

    if(state.currentState == GameState.GameSealedClass.START){
        ShuffleDeckGameScreen(secondsToStart = 4, viewModel = viewModel)
    }

    if(state.currentState in GAMESTATE_CHOOSE_BENCH_LIST){
        GameChooseBenchScreen(viewModel = viewModel)
    }

    if(state.currentState in GAMESTATE_CHOOSE_ACTIVE_LIST){
        GameChooseActiveScreen(viewModel = viewModel,)
    }

    if(state.currentState in GAMESTATE_PLAYER_TURN_LIST){
        GamePlayerTurnScreen(viewModel = viewModel)
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


