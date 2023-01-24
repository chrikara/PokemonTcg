package com.example.pokemontcg.util

sealed class Screen(val route: String) {

    object Deck : Screen(route = "deck_screen")
    object Welcome : Screen(route = "welcome_screen")
    object Main : Screen(route = "main_screen")

    object AllDecks : Screen(route = "all_decks_screen")
}