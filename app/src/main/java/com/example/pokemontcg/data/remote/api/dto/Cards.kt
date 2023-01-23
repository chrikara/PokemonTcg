package com.example.pokemontcg.data.remote.api.dto

import com.example.pokemontcg.domain.model.CardOverview

data class Cards(
    val count: Int,
    val `data`: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int
)

