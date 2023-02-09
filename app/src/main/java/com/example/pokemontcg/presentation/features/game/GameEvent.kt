package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.Player

sealed class GameEvent(){


    data class OnShuffleDeck(val player: Player) : GameEvent()
    object OnGive7CardsToEachPlayer : GameEvent()

    data class OnChooseBenchPokemon(val gameCard : GameCard) : GameEvent()

    data class OnChooseActivePokemon(val card : GameCard) : GameEvent()
    data class OnChangeGameState(val gameState : GameState.GameSealedClass) : GameEvent()

}