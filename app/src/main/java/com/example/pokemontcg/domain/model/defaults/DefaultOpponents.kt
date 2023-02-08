package com.example.pokemontcg.domain.model.defaults

import com.example.pokemontcg.R
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.domain.model.Symbol

val defaultOpponents = listOf<GymOpponent>(
    GymOpponent(
        gymName = "Pewter",
        name = "Yugo",
        image = R.drawable.yugo,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = false,
        isBeaten = false,
        defaultDeck = defaultOpponentsDecks[0],
    ),
    GymOpponent(
        gymName = "Pewter",
        name = "Rick",
        image = R.drawable.rick,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = false,
        isBeaten = false,


        ),
    GymOpponent(
        gymName = "Pewter",
        name = "Brock",
        image = R.drawable.brock,
        symbolImage = Symbol.fromString("Fighting").drawable,
        isBoss = true,
        isBeaten = false,
    ),
    GymOpponent(
        gymName = "Cerulean",
        name = "Misty",
        image = R.drawable.misty,
        symbolImage = Symbol.fromString("Water").drawable,
        isBoss = true,
        isBeaten = false,
    )
)