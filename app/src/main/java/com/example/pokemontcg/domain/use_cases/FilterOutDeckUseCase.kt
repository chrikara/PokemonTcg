package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber

class FilterOutDeckUseCase {

    // Returns cards that correspond to the chosen deck
    operator fun invoke(allCardsInRoom : List<CardSaved>, deckNumber : DeckNumber) : List<CardSaved>{
        return allCardsInRoom.filter { it.deckNumber ==  deckNumber} .sortedBy { it.nationalDex }
    }
}