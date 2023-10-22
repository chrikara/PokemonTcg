package com.example.pokemontcg.domain.model.defaults

import com.example.pokemontcg.domain.model.CardOverview
sealed class OpponentDeck(val deckList : List<CardOverview>){
    object Yugo : OpponentDeck(defaultOpponentsDecks[0])
}

val defaultOpponentsDecks = listOf<List<CardOverview>>(
    // Yugo

    listOf(
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-47", "Diglett"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
        CardOverview("base1-97", "Fighting"),
    )
)
