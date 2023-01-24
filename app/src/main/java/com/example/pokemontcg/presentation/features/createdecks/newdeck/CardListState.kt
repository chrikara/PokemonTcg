package com.example.pokemontcg.presentation.features.createdecks.newdeck


import com.example.pokemontcg.domain.model.CardOverview

data class CardListState(
    val cardList : List<CardOverview> = emptyList(),
    var isLoading : Boolean = false,
    val error : String = ""
)
