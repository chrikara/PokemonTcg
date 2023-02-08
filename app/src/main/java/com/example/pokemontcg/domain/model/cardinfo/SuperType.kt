package com.example.pokemontcg.domain.model.cardinfo

sealed class SuperType(val name : String){

    object Pokemon : SuperType("Pokémon")
    object Energy : SuperType("Energy")

    companion object {
        fun fromString(name: String): SuperType {
            return when(name) {
                "Pokémon" -> Pokemon
                "Energy" -> Energy
                else -> Energy
            }
        }
    }
}