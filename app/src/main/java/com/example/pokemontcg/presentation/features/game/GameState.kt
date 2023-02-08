package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.domain.model.Player

data class GameState(
    val player: Player = Player("Player"),
    val opponent: Player = Player("Opponent"),
    val currentState : GameEnums = GameEnums.LOADING,
    val errorMessageAPI : String = ""


){
    enum class  GameEnums{
        LOADING,
        ERROR,
        START,
        PLAYER_TURN,
        OPPONENT_TURN,
        FLIP_COIN,
        PLAYER_WON,
        OPPONENT_WON
    }
}

