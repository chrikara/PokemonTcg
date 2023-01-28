package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber

sealed class ChosenDeckEvent {

    data class GetAllCardsFromRoom(val deckNumber: DeckNumber) : ChosenDeckEvent()
    data class DeleteCardFromRoom(val cardSaved: CardSaved) : ChosenDeckEvent()
    data class ShowCardInfo(val cardSaved: CardSaved) : ChosenDeckEvent()
    data class onModifyDeckClick(val deckNumber: Int) : ChosenDeckEvent()

    data class onChangeGaugeRatio(val ratio : Float) : ChosenDeckEvent()

}