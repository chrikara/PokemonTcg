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
        sealed class CHOOSE_ACTIVE : GameSealedClass() {
            object EXPLANATION : CHOOSE_ACTIVE()
            object HAND : CHOOSE_ACTIVE()
            object CARD_INFO : CHOOSE_ACTIVE()
        }
    }


}
val GAMESTATE_CHOOSE_ACTIVE_LIST = listOf(
    GameState.GameSealedClass.CHOOSE_ACTIVE.EXPLANATION,
    GameState.GameSealedClass.CHOOSE_ACTIVE.HAND,
    GameState.GameSealedClass.CHOOSE_ACTIVE.CARD_INFO,
)

