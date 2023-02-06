package com.example.pokemontcg.domain.model

import com.example.pokemontcg.R

data class GymOpponent(
    val name : String,
    val image : Int,
    val symbolImage : Int,
    val isBoss : Boolean = false,
    val isBeaten : Boolean = false,
    val isPlayable : Boolean = true
) {


}

val defaultPewterOpponents = listOf<GymOpponent>(
    GymOpponent(
        name = "Yugo",
        image = R.drawable.yugo,
        symbolImage = Symbol.fromString("Fighting").drawable
    ),
    GymOpponent(
        name = "Rick",
        image = R.drawable.rick,
        symbolImage = Symbol.fromString("Fighting").drawable


    ),
    GymOpponent(
        name = "Brock",
        image = R.drawable.brock,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = true,
        isBeaten = false,
        isPlayable = false
    )
)