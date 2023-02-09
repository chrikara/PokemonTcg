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
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard

@Composable
fun GameChooseActive(
    viewModel: GameViewModel,
) {

    var isBackIntercepted by remember{
        mutableStateOf(true)
    }
    val state = viewModel.state
    var imageUrl by remember {
        mutableStateOf("")
    }

//    val fakePokemonList = mutableListOf<GameCard>(
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//
//    )

    BackHandler(enabled = isBackIntercepted) {
        if(state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO)
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_ACTIVE.HAND))
    }

    if(state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE.EXPLANATION){
        GameActionWindow(
            title = "ACTIVE\nPOKEMON",
            message = "Choose a basic Pokemon card as your active!",
            onClick = {
                viewModel.onEvent(
                    GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE.HAND
                ))
            }
        )
    }
    if(state.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE.HAND){
        GameHandBox(
            gameCards = state.player.currentHand,
            onClick1 = {
                viewModel.onEvent(
                    GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO)
                )
                imageUrl = it.image
            },
            onClick2 = {
                viewModel.onEvent(
                    GameEvent.OnChooseActivePokemon(it)
                )
            }
        )
    }

    if(state.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO){
        GameCardInHandBox(
            imageUrl = imageUrl,
            radius = 1500f
        )
    }
}