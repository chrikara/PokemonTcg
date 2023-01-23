package com.example.pokemontcg.data.repository

import com.example.pokemontcg.data.remote.api.PokemonTcgApi
import com.example.pokemontcg.data.remote.api.dto.Cards

import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import kotlinx.coroutines.flow.Flow

class PokemonCardsRepositoryImpl(
    private val api : PokemonTcgApi
):PokemonCardsRepository {
    override suspend fun getCards(): Cards {
        return api.getCards()
    }

    override suspend fun getCardById(cardId: String): Cards {
        return api.getCardById(cardId)
    }
}