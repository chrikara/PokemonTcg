package com.example.pokemontcg.domain.model.cardinfo

import com.example.pokemontcg.data.remote.api.dto.cardinfodto.Ability
import com.example.pokemontcg.data.remote.api.dto.cardinfodto.Attack
import com.example.pokemontcg.data.remote.api.dto.cardinfodto.Weaknesse

data class PokeInfoCard(

    val name : String = "",
    val imageUrl : String = "",
    val superType: SuperType = SuperType.Pokemon,
    val nationalDex : Int? = null,
    val description : String = "",

    val evolvesFrom : String? = null,
    val evolvesTo: String? = null,

    val types : String? = null,
    val attacks : List<Attack>? = listOf(),
    val weakness:  String? =  "",
    val hp : String = "",
    val retreatCost : List<String>? = null,
    val resistanceType : String? = null,
    val resistanceValue : String? = null

) {
}