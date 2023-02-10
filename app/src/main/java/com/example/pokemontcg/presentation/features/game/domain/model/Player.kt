package com.example.pokemontcg.presentation.features.game.domain.model

import androidx.compose.runtime.mutableStateListOf

data class Player(
    val name : String,
    var cards: MutableList<GameCard> = mutableStateListOf(),
    var currentPokemon : PokemonCard? = null,
    var currentHand : MutableList<GameCard> = mutableStateListOf(),
    var benchPokemon : MutableList<PokemonCard> = mutableStateListOf()
){



}