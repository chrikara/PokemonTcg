package com.example.pokemontcg.domain.model

import com.example.pokemontcg.R

data class GymOpponent(
    val gymName : String,
    val name : String,
    val image : Int,
    val symbolImage : Int,
    val isBoss : Boolean ,
    val isBeaten : Boolean ,
    val isPlayable : Boolean,
    val id: Int? = null
)

val defaultOpponents = listOf<GymOpponent>(
    GymOpponent(
        gymName = "Pewter",
        name = "Yugo",
        image = R.drawable.yugo,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = false,
        isBeaten = false,
        isPlayable = true
    ),
    GymOpponent(
        gymName = "Pewter",
        name = "Rick",
        image = R.drawable.rick,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = false,
        isBeaten = false,
        isPlayable = true


    ),
    GymOpponent(
        gymName = "Pewter",
        name = "Brock",
        image = R.drawable.brock,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = true,
        isBeaten = false,
        isPlayable = false
    ),
    GymOpponent(
        gymName = "Cerulean",
        name = "Misty",
        image = R.drawable.misty,
        symbolImage = Symbol.fromString("Water").drawable,
        isBoss = true,
        isBeaten = false,
        isPlayable = false
    )
)