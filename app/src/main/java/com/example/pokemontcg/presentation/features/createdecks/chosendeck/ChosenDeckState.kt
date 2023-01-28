package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.util.navigation.Screen

data class ChosenDeckState(
    val cardsSaved : List<CardSaved> = emptyList(),
    val gaugeRatio : Float = 0.0f

)
