package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusState
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.DeckNumber

sealed class ModifyDeckEvent(){

    data class OnDeletePokemonFromDeck(val cardOverview: CardOverview) : ModifyDeckEvent()
    data class OnChangedGaugeRatio(val ratio : Float) : ModifyDeckEvent()

    data class OnInsertToChosenDeck(
        val deck: DeckNumber,
        val cardOverview: CardOverview,
        val snackbarHostState: SnackbarHostState
        ) : ModifyDeckEvent()

    data class OnSearch(val query : String) : ModifyDeckEvent()
    data class OnFocused(val focusState : FocusState) : ModifyDeckEvent()
    data class OnSearchBarClicked(val focusState: FocusState) : ModifyDeckEvent()
    object OnBackPressed : ModifyDeckEvent()
    data class OnTextFieldChange(val query : String) : ModifyDeckEvent()
    object OnSearchBarExpanded : ModifyDeckEvent()


}