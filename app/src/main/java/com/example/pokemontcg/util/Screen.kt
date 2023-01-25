package com.example.pokemontcg.util

sealed class Screen(val route: String) {

    object DeckModify : Screen(route = "deck_modify_screen")
    object Welcome : Screen(route = "welcome_screen")
    object Main : Screen(route = "main_screen")

    object AllDecks : Screen(route = "all_decks_screen")
    object ChosenDeck : Screen(route = "my_deck_screen")

    object PokeCardInfo : Screen(route = "poke_card_info_screen")
}