package com.example.pokemontcg.presentation.features.game.domain.model

data class GameAttack(
    val convertedEnergyCost: Int,
    val cost: List<String>,
    val damage: String,
    val name: String,
    val text: String,
    val hasFlipCoin : Boolean = false
)