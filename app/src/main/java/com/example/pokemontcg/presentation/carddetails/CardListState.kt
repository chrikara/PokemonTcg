package com.example.pokemontcg.presentation.carddetails


import com.example.pokemontcg.data.remote.api.dto.Cards
import com.example.pokemontcg.data.remote.api.dto.Data
import com.example.pokemontcg.domain.model.CardOverview

data class CardListState(
    val cardList : List<CardOverview> = emptyList()
)
