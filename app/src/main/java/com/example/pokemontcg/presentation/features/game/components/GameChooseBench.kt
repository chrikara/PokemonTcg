package com.example.pokemontcg.presentation.features.game.components

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel

@Composable
fun GameChooseBench(
    viewModel : GameViewModel
) {

    var imageUrl by remember {
        mutableStateOf("")
    }
    var isBackIntercepted by remember{
        mutableStateOf(true)
    }
    val state = viewModel.state

    BackHandler(enabled = isBackIntercepted) {
        if(state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO)
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.HAND))
    }

    if(state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.EXPLANATION){
        GameActionWindow(
            title = "BENCH POKEMON",
            message = "Choose bench pokemon if you have any!"
        ) {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.HAND))
        }
    }

    if(state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.HAND){
        println("STATE1 ${viewModel.state.player.benchPokemon}")
        GameHandBox(
            viewModel = viewModel,
            textButton2 = "Add Bench",
            textButton3 = "End",
            onClick1 = {
                viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO))
                imageUrl = it.image
                println(GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO==GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO)
            },
            onClick2 = {
                viewModel.onEvent(GameEvent.OnPlayerBenchPokemon(it))
            },
            onClick3 = {
                viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.EXPLANATION))
                viewModel.onEvent(GameEvent.OnOpponentBenchAllPokemon)
            },
            isButton3Visible = true,

        )
    }
    if(state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO){
        GameCardInHandBox(
            imageUrl = imageUrl,
            radius = 1500f
        )
        println(imageUrl)
    }
}