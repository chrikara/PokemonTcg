package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn

import com.example.pokemontcg.presentation.features.game.domain.model.GameCard

data class GamePlayerTurnState(
    val isAttackButtonEnabled : Boolean = false,
    val cardInfoImage : String = "",
    val selectedCardIndex :Int = 0,
    val currentHandSize : Int = -1

)