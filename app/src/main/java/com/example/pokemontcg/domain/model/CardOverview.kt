package com.example.pokemontcg.domain.model

import com.example.pokemontcg.domain.model.cardinfo.SuperType

data class CardOverview(
    val id: String,
    val name : String,
    val imgString : String = "",
    val superType: SuperType = SuperType.Pokemon,
    val nationalDex : Int? = -1
)
