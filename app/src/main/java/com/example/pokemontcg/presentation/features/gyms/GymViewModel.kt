package com.example.pokemontcg.presentation.features.gyms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.domain.model.defaultPewterOpponents
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GymViewModel @Inject constructor(
    val repository: PokemonCardsRepository
) : ViewModel(){

    var state by mutableStateOf<GymState>(GymState())
        private set

    init {
        state = state.copy(
            selectedOpponent = defaultPewterOpponents.first(),
            selectedDeck = defaultDecks.first()
        )
    }

    fun onEvent(event: GymEvent){
        when(event){
            is GymEvent.OnNextOpponent ->{
                if(state.selectedOpponent == defaultPewterOpponents.last()){
                    return
                }

                val currentOpponentIndex = defaultPewterOpponents.indexOf(state.selectedOpponent)

                state = state.copy(
                    selectedOpponent = defaultPewterOpponents[currentOpponentIndex + 1],
                    isPreviousOpponentEnabled = true,
                    isNextOpponentEnabled = if(defaultPewterOpponents[currentOpponentIndex + 1] == defaultPewterOpponents.last()) false else true
                )
            }
            GymEvent.OnPreviousOpponent -> {
                if(state.selectedOpponent == defaultPewterOpponents.first()){
                    return
                }

                val currentOpponentIndex = defaultPewterOpponents.indexOf(state.selectedOpponent)



                state = state.copy(
                    selectedOpponent = defaultPewterOpponents[currentOpponentIndex - 1],
                    isPreviousOpponentEnabled = if(defaultPewterOpponents[currentOpponentIndex-1] == defaultPewterOpponents.first()) false else true,
                    isNextOpponentEnabled = true

                )
            }

            GymEvent.OnNextDeck -> {
                if(state.selectedDeck == defaultDecks.last()){
                    return
                }
                val currentOpponentIndex = defaultDecks.indexOf(state.selectedDeck)

                state = state.copy(
                    selectedDeck = defaultDecks[currentOpponentIndex + 1],
                    isPreviousDeckEnabled = true,
                    isNextDeckEnabled = if(defaultDecks[currentOpponentIndex + 1] == defaultDecks.last()) false else true
                )
            }
            GymEvent.OnPreviousDeck -> {
                if(state.selectedDeck == defaultDecks.first()){
                    return
                }
                val currentOpponentIndex = defaultDecks.indexOf(state.selectedDeck)

                state = state.copy(
                    selectedDeck = defaultDecks[currentOpponentIndex - 1],
                    isPreviousDeckEnabled = if(defaultDecks[currentOpponentIndex - 1] == defaultDecks.first()) false else true,
                    isNextDeckEnabled = true
                )
            }

            is GymEvent.OnDeckClicked -> {
                state = state.copy(
                    selectedDeck = event.deckNumber,
                    isPreviousDeckEnabled = if(event.deckNumber == defaultDecks.first()) false else true,
                    isNextDeckEnabled = if(event.deckNumber == defaultDecks.last()) false else true,
                    )
            }
        }
    }
}