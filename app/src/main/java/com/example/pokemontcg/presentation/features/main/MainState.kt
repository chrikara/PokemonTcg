package com.example.pokemontcg.presentation.features.main

import com.example.pokemontcg.domain.model.GymOpponent

data class MainState(
    val opponentsList : List<GymOpponent> = emptyList()
) {
}