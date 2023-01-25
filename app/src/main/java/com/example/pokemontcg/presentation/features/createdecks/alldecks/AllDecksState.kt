package com.example.pokemontcg.presentation.features.createdecks.alldecks

import com.example.pokemontcg.domain.model.CardSaved

data class AllDecksState(
    val cardsSaved : List<CardSaved> = emptyList()
) {
}