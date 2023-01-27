package com.example.pokemontcg.data.remote.api

import com.example.pokemontcg.data.remote.api.dto.cardinfodto.CardInfoDto
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Cards

import com.example.pokemontcg.util.DangerousConstants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface PokemonTcgApi {

    // Firstly, we go to the site ("/v1/coins") we are interested in, get one product and use the plugin JSON to Kotlin to get
    // the data class. You don't need every parameter on that class, so you create a separate data class in the Model package
    // with the desired parameters.

    // Then, use an extension function in CoinDto to map CoinDto to Coin

    // The same for ("/v1/coins/{coinId})

    @Headers("X-Api-Key:${API_KEY}")
    @GET("v2/cards?q=set.id:base1")
    suspend fun getCards() : Cards

    @Headers("X-Api-Key:${API_KEY}")
    @GET("v2/cards/{cardId}")
    suspend fun getCardById(@Path("cardId") cardId : String) : CardInfoDto

}
