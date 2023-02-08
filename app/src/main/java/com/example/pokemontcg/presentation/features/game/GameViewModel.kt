package com.example.pokemontcg.presentation.features.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.presentation.features.game.model.GameCard
import com.example.pokemontcg.presentation.features.game.model.GameDeck
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private var getCardsForOneDeckJob: Job? = null
    var state by mutableStateOf(GameState())

    init {
        val deckNumber = DeckNumber.fromString(savedStateHandle.get<String>("deckNumber")?: "1st")
        val opponent = savedStateHandle.get<String>("opponent") ?: "Yugo"
        getCardsForOneDeck(deckNumber)
    }

    fun getCardsForOneDeck(deckNumber : DeckNumber) {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->
            val cardsSavedDeck = cardsSaved.filter { it.deckNumber == deckNumber }

            state = state.copy(
                playerDeck = GameDeck(
                    cards = cardsSavedDeck.map { GameCard(it.pokemonName) }
                )
            )
            println(state)

        }.launchIn(viewModelScope)
    }


}