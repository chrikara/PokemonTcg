package com.example.pokemontcg.data.remote.api.dto.cardinfodto

import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Data
import com.example.pokemontcg.domain.model.CardOverview

data class Data(
    val abilities: List<Ability>,
    val artist: String,
    val attacks: List<Attack>,
    val cardmarket: Cardmarket,
    val convertedRetreatCost: Int,
    val evolvesFrom: String,
    val flavorText: String,
    val hp: String,
    val id: String,
    val images: Images,
    val legalities: Legalities,
    val level: String,
    val name: String,
    val nationalPokedexNumbers: List<Int>?,
    val number: String,
    val rarity: String,
    val retreatCost: List<String>,
    val `set`: Set,
    val subtypes: List<String>,
    val supertype: String,
    val tcgplayer: Tcgplayer,
    val types: List<String>?,
    val weaknesses: List<Weaknesse>
)

