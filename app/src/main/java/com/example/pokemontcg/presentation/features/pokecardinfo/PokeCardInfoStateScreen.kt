package com.example.pokemontcg.presentation.features.pokecardinfo

import com.example.pokemontcg.domain.model.cardinfo.EnergyInfoCard
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.domain.model.cardinfo.TrainerInfoCard

data class PokeCardInfoStateScreen(

    val trainerInfoCard: TrainerInfoCard? = null,
    val pokeInfoCard: PokeInfoCard? = null,
    val energyInfoCard: EnergyInfoCard? = null,
    val isLoading : Boolean = false,
    val error : String = ""
)