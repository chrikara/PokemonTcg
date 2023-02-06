package com.example.pokemontcg.presentation.features.gyms

import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.Gym

sealed class GymEvent {


    object OnNextOpponent : GymEvent()
    object OnPreviousOpponent : GymEvent()

    object OnNextDeck : GymEvent()
    object OnPreviousDeck : GymEvent()

    data class OnDeckClicked(val deckNumber: DeckNumber) : GymEvent()
}