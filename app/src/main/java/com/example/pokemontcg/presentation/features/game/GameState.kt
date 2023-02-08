package com.example.pokemontcg.presentation.features.game

import com.example.pokemontcg.presentation.features.game.model.GameDeck

data class GameState(
    val playerDeck : GameDeck = GameDeck(cards = emptyList())
)