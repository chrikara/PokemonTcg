package com.example.pokemontcg.presentation.features.game.domain.model

import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Attack
import com.example.pokemontcg.domain.model.Symbol


abstract class GameCard(
    val baseId : String,
    val name: String,
    val image : String)
class EnergyCard(
    baseId: String = "",
    name : String = "",
    image : String = "",
    val symbol: Symbol = Symbol.Colorless

)
    : GameCard(baseId, name, image)
class PokemonCard(
    baseId: String,
    name: String,
    image: String = "",
    val nationalDex : Int = 2,
    val pokemonType: PokemonType,
    val canEvolve : () -> Boolean = {false},
    val attack: List<Attack>? = null,
) : GameCard(baseId, name, image){

    var energyAttached = mutableListOf<EnergyCard>(EnergyCard(symbol = Symbol.Psychic, name = "Psychic"))
    var totalHp: Int = 0
    var remainingHp : Int = 0

    fun attachEnergy(energy : EnergyCard){
        if(energy == EnergyCard(symbol = Symbol.Colorless)){
            energyAttached.add(energy)
            energyAttached.add(energy)
            return
        }

        energyAttached.add(energy)
    }

    fun performAttack(attack: Attack){

    }

}

sealed class PokemonType(){
    object Basic : PokemonType()
    object Stage1 : PokemonType()
    object Stage2 : PokemonType()
}