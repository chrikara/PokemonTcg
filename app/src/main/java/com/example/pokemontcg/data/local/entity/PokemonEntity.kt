package com.example.pokemontcg.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemontcg.domain.model.CardSaved


@Entity
data class PokemonEntity(
    val deckNumber : Int,
    val pokemonName : String,
    val pokemonId : String,
    val pokemonImageUrl : String,
    val nationalDex : Int,
    @PrimaryKey val id: Int? = null
)

fun PokemonEntity.toCardSaved() : CardSaved{
    return CardSaved(
        deckNumber = this.deckNumber,
        pokemonName = this.pokemonName,
        pokemonId = this.pokemonId,
        pokemonImageUrl = this.pokemonImageUrl,
        nationalDex = this.nationalDex,
        id = this.id
    )
}