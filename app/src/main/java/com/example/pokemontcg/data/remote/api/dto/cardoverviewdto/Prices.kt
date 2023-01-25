package com.example.pokemontcg.data.remote.api.dto.cardoverviewdto

data class Prices(
    val averageSellPrice: Double,
    val avg1: Double,
    val avg30: Double,
    val avg7: Double,
    val lowPrice: Double,
    val lowPriceExPlus: Double,
    val reverseHoloTrend: Double,
    val trendPrice: Double
)