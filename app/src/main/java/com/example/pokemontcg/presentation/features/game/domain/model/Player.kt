package com.example.pokemontcg.presentation.features.game.domain.model

data class Player(
    val name : String,
    var cards: MutableList<GameCard> = mutableListOf(),
    var currentPokemon : PokemonCard? = null,
    var currentHand : MutableList<GameCard> = mutableListOf()
){



}