package com.example.pokemontcg.presentation.features.game

import androidx.compose.material3.SnackbarHostState
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.choosebench.GameChooseBenchViewModel

sealed class GameEvent(){


    data class OnShuffleDeck(val player: Player) : GameEvent()
    object OnGive7CardsToEachPlayer : GameEvent()

    data class OnPlayerBenchPokemon(
        val gameCard : GameCard,
        val snackbarHostState: SnackbarHostState,
        val viewModelBench: GameChooseBenchViewModel
        ) : GameEvent()

    data class OnChooseActivePokemon(
        val card : GameCard,
        val snackbarHostState: SnackbarHostState
        ) : GameEvent()
    data class OnChangeGameState(val gameState : GameState.GameSealedClass) : GameEvent()
    object OnOpponentBenchAllPokemon : GameEvent()

}