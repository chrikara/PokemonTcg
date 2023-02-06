package com.example.pokemontcg.domain.repository

import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.data.remote.api.dto.cardinfodto.CardInfoDto
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Cards
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.GymOpponent
import kotlinx.coroutines.flow.Flow


interface PokemonCardsRepository {

    suspend fun getCards() : Cards
    suspend fun getCardById(cardId : String) : CardInfoDto

    suspend fun deleteCardPokemonFromDeck(cardSaved: CardSaved)
    suspend fun insertCardPokemonToDeck(cardSaved: PokemonEntity)

    fun getPokemonFromDeck() : Flow<List<CardSaved>>

    suspend fun insertGymOpponent(opponent: GymOpponent)
    suspend fun deleteGymOpponent(opponent: GymOpponent)

    fun getAllGymOpponents() : Flow<List<GymOpponent>>
}