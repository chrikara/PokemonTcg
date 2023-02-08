package com.example.pokemontcg.presentation.features.game

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemontcg.presentation.features.gyms.GymEvent

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {

    val state = viewModel.state
    println("Mpike gia state $state")

    if(state.currentState == GameState.GameEnums.START){


        viewModel.onEvent(GameEvent.OnShuffleDeck(state.player))
        viewModel.onEvent(GameEvent.OnShuffleDeck(state.opponent))
        viewModel.onEvent(GameEvent.OnGive7CardsToEachPlayer)


    }




}