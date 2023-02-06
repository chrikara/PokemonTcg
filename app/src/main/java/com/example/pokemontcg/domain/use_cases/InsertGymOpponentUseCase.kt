package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.domain.repository.PokemonCardsRepository

class InsertGymOpponentUseCase(
    private val repository: PokemonCardsRepository
) {

    suspend operator fun invoke(opponent: GymOpponent){
        repository.insertGymOpponent(opponent)
    }
}