package com.example.pokemontcg.presentation.features.createdecks.modifydeck


import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.CardSaved

data class ModifyDeckState(
    val savedCardList : List<CardSaved> = emptyList(),
    val cardList : List<CardOverview> = emptyList(),
    var isLoading : Boolean = false,
    val error : String = "",
    val gaugeRatio : Float = 0f
)
