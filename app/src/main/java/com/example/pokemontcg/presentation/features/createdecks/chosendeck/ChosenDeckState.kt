package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import com.example.pokemontcg.domain.model.CardSaved

data class ChosenDeckState(
    val cardsSaved : List<CardSaved> = emptyList()
)
