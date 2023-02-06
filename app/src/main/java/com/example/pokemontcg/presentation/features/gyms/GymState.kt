package com.example.pokemontcg.presentation.features.gyms

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.GymOpponent

data class GymState(


    val savedCardList : List<CardSaved> = emptyList(),
    val opponentsForThisGym: List<GymOpponent> = emptyList(),

    val isLoadingDb : Boolean = true,

    val isNextOpponentEnabled : Boolean = true,
    val isPreviousOpponentEnabled : Boolean = false,
    val isNextDeckEnabled : Boolean = true,
    val isPreviousDeckEnabled : Boolean = false,
    val selectedOpponent : GymOpponent? = null,
    val selectedDeck : DeckNumber = DeckNumber.First

)