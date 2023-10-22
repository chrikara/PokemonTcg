package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn

import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard

sealed class PlayerTurnEvent(){

    object OnAttackButtonTriggered : PlayerTurnEvent()
    data class OnClickSelectedCardBasicPokemon(
        val gameCard: GameCard,
        val viewModelGame : GameViewModel
        ) : PlayerTurnEvent()

    data class OnCardInfoImage(val imageUrl : String) : PlayerTurnEvent()
    data class OnSelectedCardIndex(val index: Int) : PlayerTurnEvent()
    data class OnClickHand(
        val currentHand: MutableList<GameCard>,
        val onShowHand :() -> Unit
        ) : PlayerTurnEvent()
}
