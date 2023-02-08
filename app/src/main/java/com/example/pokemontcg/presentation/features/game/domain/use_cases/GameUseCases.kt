package com.example.pokemontcg.presentation.features.game.domain.use_cases

data class GameUseCases(
    val shuffle : ShuffleUseCase,
    val drawCards: DrawCardsUseCase
)
