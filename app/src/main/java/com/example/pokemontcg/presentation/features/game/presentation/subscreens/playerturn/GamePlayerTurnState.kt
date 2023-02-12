package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn

data class GamePlayerTurnState(
    val isAttackButtonEnabled : Boolean = false,
    val cardInfoImage : String = "",
    val selectedCardIndex :Int = 0

)