package com.example.pokemontcg.presentation.features.game.presentation.components

import androidx.activity.compose.BackHandler
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.choosebench.GameChooseBenchEvent
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.choosebench.GameChooseBenchViewModel

@Composable
fun GameChooseBenchScreen(
    viewModelGame : GameViewModel,
    viewModelBench : GameChooseBenchViewModel,
    snackbarHostState: SnackbarHostState
) {

    var isBackIntercepted by remember{
        mutableStateOf(true)
    }

    val stateGame = viewModelGame.state
    val stateBench = viewModelBench.state

    BackHandler(enabled = isBackIntercepted) {
        if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO)
            viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.HAND))
    }

    if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_BENCH.EXPLANATION){
        GameActionWindow(
            title = "BENCH POKEMON",
            message = "Choose bench pokemon if you have any!"
        ) {
            viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.HAND))
        }
    }

    if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_BENCH.HAND){
        println("STATE1 ${viewModelGame.state.player.benchPokemon}")
        GameHandBox(
            viewModel = viewModelGame,
            textButton1 = "Info",
            textButton2 = "Add Bench",
            textButton3 = "End",
            selectedIndex = stateBench.selectedCardIndex,
            onClick1 = {
                viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO))
                viewModelBench.onEvent(GameChooseBenchEvent.OnCardInfoImage(it.image))

            },
            onClick2 = {
                viewModelGame.onEvent(GameEvent.OnPlayerBenchPokemon(
                    gameCard = it,
                    snackbarHostState = snackbarHostState,
                    viewModelBench = viewModelBench
                ))



            },
            onClick3 = {
                viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.EXPLANATION))
                viewModelGame.onEvent(GameEvent.OnOpponentBenchAllPokemon)
            },
            isButton3Visible = true,
            onItemClick = {
                viewModelBench.onEvent(GameChooseBenchEvent.OnItemSelected(stateGame.player.currentHand.indexOf(it)))
            }


        )
    }
    if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_BENCH.CARD_INFO){
        GameCardInfoBox(
            imageUrl = stateBench.cardInfoImage,
            radius = 1500f
        )
    }
}