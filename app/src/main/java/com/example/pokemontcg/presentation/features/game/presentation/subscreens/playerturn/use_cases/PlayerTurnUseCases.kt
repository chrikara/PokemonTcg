package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases

data class PlayerTurnUseCases(
    val cardTextInHand : CardTextInHandUseCase,
    val actionInHand : ActionInHandUseCase
)