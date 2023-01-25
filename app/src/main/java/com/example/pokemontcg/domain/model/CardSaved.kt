package com.example.pokemontcg.domain.model

import androidx.room.PrimaryKey
import com.example.pokemontcg.data.local.entity.PokemonEntity
import com.example.pokemontcg.data.local.entity.toCardSaved

data class CardSaved(
    val deckNumber : Int,
    val pokemonName : String,
    val pokemonId : String,
    val pokemonImageUrl : String,
    val nationalDex : Int,
    val id: Int? = null
)

fun CardSaved.toPokemonEntity() : PokemonEntity {
    return PokemonEntity(
        deckNumber = this.deckNumber,
        pokemonName = this.pokemonName,
        pokemonId = this.pokemonId,
        pokemonImageUrl = this.pokemonImageUrl,
        nationalDex = this.nationalDex,
        id = this.id
    )
}
