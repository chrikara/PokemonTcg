package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.pokemontcg.domain.model.Evolution
import com.example.pokemontcg.domain.model.cardinfo.EnergyInfoCard
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.domain.model.cardinfo.TrainerInfoCard

data class PokeCardInfoStateScreen(

    val pokeInfoCard: PokeInfoCard? = null,
    val energyInfoCard: EnergyInfoCard? = null,
    val evolution: Evolution = Evolution.None,
    val isLoading : Boolean = false,
    val error : String = "",

    val initialAnimationSize : Dp = 1000.dp
)