package com.example.pokemontcg.domain.use_cases

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGymOpponentsUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    operator fun invoke() : Flow<List<GymOpponent>> {
        return repository.getAllGymOpponents()
    }
}