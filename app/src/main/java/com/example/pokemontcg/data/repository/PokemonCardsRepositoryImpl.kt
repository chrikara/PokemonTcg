package com.example.pokemontcg.data.repository

import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.data.local.entity.TrackerDao
import com.example.pokemontcg.data.local.entity.toCardSaved
import com.example.pokemontcg.data.remote.api.PokemonTcgApi
import com.example.pokemontcg.data.remote.api.dto.Cards
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.toPokemonEntity

import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonCardsRepositoryImpl(
    private val api : PokemonTcgApi,
    private val dao : TrackerDao
):PokemonCardsRepository {
    override suspend fun getCards(): Cards {
        return api.getCards()
    }

    override suspend fun getCardById(cardId: String): Cards {
        return api.getCardById(cardId)
    }

    override suspend fun deleteCardPokemonFromDeck(cardSaved: CardSaved) {
        return dao.deletePokemonFromDeck(cardSaved.toPokemonEntity())
    }

    override suspend fun insertCardPokemonToDeck(entity: PokemonEntity) {
        return dao.insertPokemonToDeck(entity)
    }

    override fun getPokemonFromDeck(): Flow<List<CardSaved>> {
        return dao.getPokemonFromDeck().map { entities->
            entities.map { it.toCardSaved() }
        }
    }
}