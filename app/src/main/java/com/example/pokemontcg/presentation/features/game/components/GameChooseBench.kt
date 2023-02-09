package com.example.pokemontcg.presentation.features.game.components

import androidx.compose.runtime.Composable
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel

@Composable
fun GameChooseBench(
    viewModel : GameViewModel
) {


    if(viewModel.state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.EXPLANATION){
        GameActionWindow(
            title = "BENCH POKEMON",
            message = "Choose bench pokemon if you have any!"
        ) {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.HAND))
        }
    }

    if(viewModel.state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.HAND){
        println("STATE1 ${viewModel.state.player.benchPokemon}")
        GameHandBox(
            gameCards = viewModel.state.player.currentHand,
            onClick1 = {},
            onClick2 = {

                viewModel.onEvent(GameEvent.OnChooseBenchPokemon(it))
                println("STATE2 ${viewModel.state.currentState}")
            }
        )

    }
}