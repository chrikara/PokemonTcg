package com.example.pokemontcg.presentation.features.createdecks.use_cases

import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import javax.inject.Inject

class InsertPokemonToDeckUseCase @Inject constructor(
    private val repository: PokemonCardsRepository
) {

    suspend operator fun invoke(
        deckNumber: Int,
        pokemonId : String,
        pokemonName : String,
        pokemonImageUrl : String,
        nationalDex : Int

    ){
        repository.insertCardPokemonToDeck(
            PokemonEntity(
                deckNumber = deckNumber,
                pokemonId = pokemonId,
                pokemonName = pokemonName,
                pokemonImageUrl = pokemonImageUrl,
                nationalDex = nationalDex
            )
        )
    }
}