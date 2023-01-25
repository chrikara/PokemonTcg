package com.example.pokemontcg.data.remote.api.dto.cardoverviewdto

data class Cards(
    val count: Int,
    val `data`: List<Data>,
    val page: Int,
    val pageSize: Int,
    val totalCount: Int
)

