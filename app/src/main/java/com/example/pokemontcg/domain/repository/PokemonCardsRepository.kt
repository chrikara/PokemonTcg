package com.example.pokemontcg.domain.repository

import com.example.pokemontcg.data.remote.api.dto.Cards


interface PokemonCardsRepository {



    suspend fun getCards() : Cards
    suspend fun getCardById(cardId : String) : Cards

}