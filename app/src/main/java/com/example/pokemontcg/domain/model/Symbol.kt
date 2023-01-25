package com.example.pokemontcg.domain.model

import androidx.compose.ui.graphics.Color
import com.example.pokemontcg.R
import com.example.pokemontcg.ui.theme.TypeColorless
import com.example.pokemontcg.ui.theme.TypeElectric
import com.example.pokemontcg.ui.theme.TypeFighting
import com.example.pokemontcg.ui.theme.TypeFire
import com.example.pokemontcg.ui.theme.TypeGrass
import com.example.pokemontcg.ui.theme.TypePsychic
import com.example.pokemontcg.ui.theme.TypeWater

sealed class Symbol(
    val drawable : Int,
    val color : Color
){

    object Fire : Symbol(drawable = R.drawable.symbol_fire, color = TypeFire)
    object Electric : Symbol(drawable = R.drawable.symbol_electric, color = TypeElectric)
    object Colorless : Symbol(drawable = R.drawable.symbol_colorless, color = TypeColorless)
    object Grass : Symbol(drawable = R.drawable.symbol_grass, color = TypeGrass)
    object Water : Symbol(drawable = R.drawable.symbol_water, color = TypeWater)
    object Psychic : Symbol(drawable = R.drawable.symbol_psychic, color = TypePsychic)
    object Fighting : Symbol(drawable = R.drawable.symbol_fighting, color = TypeFighting)

    companion object{
        fun fromString(type : String) : Symbol{
            return when(type){
                "Fire" -> Fire
                "Lightning" -> Electric
                "Colorless" -> Colorless
                "Grass" -> Grass
                "Water" -> Water
                "Psychic" -> Psychic
                else -> Fighting
            }
        }
    }
}
