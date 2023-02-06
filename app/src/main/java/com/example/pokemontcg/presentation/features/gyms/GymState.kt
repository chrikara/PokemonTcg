package com.example.pokemontcg.presentation.features.gyms

import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.GymOpponent

data class GymState(

    val isNextOpponentEnabled : Boolean = true,
    val isPreviousOpponentEnabled : Boolean = false,
    val isNextDeckEnabled : Boolean = true,
    val isPreviousDeckEnabled : Boolean = false,
    val selectedOpponent : GymOpponent? = null,
    val selectedDeck : DeckNumber = DeckNumber.First

)