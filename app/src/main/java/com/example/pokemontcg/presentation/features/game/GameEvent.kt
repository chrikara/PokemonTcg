package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.domain.model.Player

sealed class GameEvent(){


    data class OnShuffleDeck(val player: Player) : GameEvent()
    object OnGive7CardsToEachPlayer : GameEvent()

    data class OnChooseActivePokemon(val player : Player) : GameEvent()
    data class OnChangeGameState(val gameState : GameState.GameSealedClass) : GameEvent()

}