package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.material3.SnackbarHostState
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.DeckNumber

sealed class ModifyDeckEvent(){

    data class OnGetCardsForOneDeck(val deckNumber : DeckNumber) : ModifyDeckEvent()
    data class OnDeletePokemonFromDeck(val cardOverview: CardOverview) : ModifyDeckEvent()
    data class OnChangedGaugeRatio(val ratio : Float) : ModifyDeckEvent()

    data class OnInsertToChosenDeck(
        val deck: DeckNumber,
        val cardOverview: CardOverview,
        val snackbarHostState: SnackbarHostState
        ) : ModifyDeckEvent()




}