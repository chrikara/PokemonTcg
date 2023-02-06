package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.domain.repository.PokemonCardsRepository

class DeleteOpponentFromDbUseCase(
    private val repository: PokemonCardsRepository
) {

    suspend operator fun invoke(){
        repository.deleteAllGymOpponents()
    }
}