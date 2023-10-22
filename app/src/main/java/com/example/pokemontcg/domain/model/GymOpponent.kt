package com.example.pokemontcg.domain.model

import com.example.pokemontcg.R
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard

data class GymOpponent(
    val gymName : String,
    val name : String,
    val image : Int,
    val symbolImage : Int,
    val isBoss : Boolean ,
    val isBeaten : Boolean ,
    val id: Int? = null,
    val defaultDeck : List<CardOverview> = emptyList()
)

