package com.example.pokemontcg.presentation.features.createdecks.alldecks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class AllDecksViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases
) : ViewModel() {
    var state by mutableStateOf<AllDecksState>(AllDecksState())
        private set

    private var getCardsForOneDeckJob : Job? = null

    init {
        getCardsForOneDeck()
    }

    fun getCardsForOneDeck() {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->

            state = state.copy(
                cardsSaved = cardsSaved
            )

        }.launchIn(viewModelScope)
    }
}