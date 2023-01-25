package com.example.pokemontcg.domain.model.cardinfo

sealed class SuperType(val name : String){

    object Pokemon : SuperType("Pokémon")
    object Trainer : SuperType("Trainer")
    object Energy : SuperType("Energy")

    companion object {
        fun fromString(name: String): SuperType {
            return when(name) {
                "Pokémon" -> Pokemon
                "Trainer" -> Trainer
                "Energy" -> Energy
                else -> Energy
            }
        }
    }
}