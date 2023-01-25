package com.example.pokemontcg.data.remote.api.dto.cardinfodto

data class Attack(
    val convertedEnergyCost: Int,
    val cost: List<String>,
    val damage: String,
    val name: String,
    val text: String
)