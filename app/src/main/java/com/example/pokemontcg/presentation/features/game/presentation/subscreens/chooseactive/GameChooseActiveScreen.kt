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
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.chooseactive.GameChooseActiveViewModel

@Composable
fun GameChooseActiveScreen(
    viewModelGame: GameViewModel,
    viewModelActive: GameChooseActiveViewModel,
    snackbarHostState: SnackbarHostState
) {

    var isBackIntercepted by remember{
        mutableStateOf(true)
    }
    val stateGame = viewModelGame.state
    val stateActive = viewModelActive.state


//    val fakePokemonList = mutableListOf<GameCard>(
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//
//    )

    BackHandler(enabled = isBackIntercepted) {
        if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO)
            viewModelGame.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_ACTIVE.HAND))
    }

    if(stateGame.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE.EXPLANATION){
        GameActionWindow(
            title = "ACTIVE\nPOKEMON",
            message = "Choose a basic Pokemon card as your active!",
            onClick = {
                viewModelGame.onEvent(
                    GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE.HAND
                ))
            }
        )
    }
    if(stateGame.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE.HAND){
        GameHandBox(
            viewModel = viewModelGame,
            textButton1 = "Info",
            textButton2 = "Add Active",
            onClick1 = {
                viewModelGame.onEvent(
                    GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO)
                )
                viewModelActive.onCardInfoImage(it.image)
            },
            onClick2 = {
                viewModelGame.onEvent(
                    GameEvent.OnChooseActivePokemon(
                        card = it,
                        snackbarHostState = snackbarHostState
                    )
                )
            },
            onItemClick = {
                viewModelActive.onItemSelected(stateGame.player.currentHand.indexOf(it))
            },
            selectedIndex = stateActive.selectedCardIndex
        )
    }

    if(stateGame.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO){
        GameCardInfoBox(
            imageUrl = stateActive.cardInfoImage,
            radius = 1500f
        )
    }
}