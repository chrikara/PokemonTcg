package com.example.pokemontcg.presentation.features.createdecks.use_cases

import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPokemonFromDeckUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    operator fun invoke() : Flow<List<CardSaved>> {
        return repository.getPokemonFromDeck()
    }
}