package com.example.pokemontcg.presentation.features.createdecks.use_cases

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.repository.PokemonCardsRepository

class DeletePokemonFromDeckUseCase(
    private val repository: PokemonCardsRepository
) {

    suspend operator fun invoke(cardSaved: CardSaved){
        repository.deleteCardPokemonFromDeck(cardSaved)
    }
}