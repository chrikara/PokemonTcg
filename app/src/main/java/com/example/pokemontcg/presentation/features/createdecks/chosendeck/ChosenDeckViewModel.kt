package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonFromDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChosenDeckViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases
) : ViewModel() {

    var state by mutableStateOf<ChosenDeckState>(ChosenDeckState())
        private set

    private var getCardsForOneDeckJob: Job? = null




    fun getCardsForOneDeck(deckNumber: DeckNumber) {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->

        state = state.copy(
            cardsSaved = cardsSaved.filter { it.deckNumber == deckNumber } .sortedBy { it.nationalDex }
        )

        }.launchIn(viewModelScope)
    }

    fun deleteCardFromDeck(cardSaved: CardSaved){
        viewModelScope.launch {
            allMyDeckUseCases.deletePokemonFromDeckUseCase(cardSaved)
        }
    }




}