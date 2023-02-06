package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.domain.repository.PokemonCardsRepository

class DeleteAllPokemonCardsUseCase(
    private val repository: PokemonCardsRepository
) {

    suspend operator fun invoke(){
        repository.deleteAllPokemonCards()
    }
}