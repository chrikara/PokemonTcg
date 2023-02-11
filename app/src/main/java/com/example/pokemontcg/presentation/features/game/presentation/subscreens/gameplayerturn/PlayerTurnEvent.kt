package com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn

sealed class PlayerTurnEvent(){

    object OnAttackButtonTriggered : PlayerTurnEvent()
    data class OnCardInfoImage(val imageUrl : String) : PlayerTurnEvent()
}
