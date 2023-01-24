package com.example.pokemontcg.domain.model

data class CardOverview(
    val id: String,
    val name : String,
    val imgString : String,
    val type : String? = null,
    val nationalDex : Int? = -1
)
