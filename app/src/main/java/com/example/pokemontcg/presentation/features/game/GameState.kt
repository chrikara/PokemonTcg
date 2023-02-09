package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.domain.model.Player

data class GameState(
    val player: Player = Player("Player",),
    val opponent: Player = Player("Opponent"),
    val currentState : GameSealedClass = GameSealedClass.LOADING,
    val errorMessageAPI : String = "",
    val isBackIntercepted : Boolean = false,

    ){

    sealed class GameSealedClass {
        object LOADING : GameSealedClass()
        object ERROR : GameSealedClass()
        object START : GameSealedClass()
        object PLAYER_TURN : GameSealedClass()
        object OPPONENT_TURN : GameSealedClass()
        object FLIP_COIN : GameSealedClass()
        object PLAYER_WON : GameSealedClass()
        object OPPONENT_WON : GameSealedClass()
        data class CHOOSE_ACTIVE(val chooseActive: CHOOSE_ACTIVE_STATE) : GameSealedClass()
    }
    enum class CHOOSE_ACTIVE_STATE{
        EXPLANATION,
        HAND,
        CARD_INFO
    }

}

