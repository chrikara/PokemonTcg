package com.example.pokemontcg.presentation.features.gyms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.defaultGyms
import com.example.pokemontcg.domain.model.defaultPewterOpponents
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.domain.use_cases.FilterOutDeckUseCase
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL
import com.example.pokemontcg.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val filterOutDeckUseCase: FilterOutDeckUseCase
) : ViewModel(){

    var state by mutableStateOf<GymState>(GymState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        getCardsFromDb()

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

            GymEvent.OnPlay -> {
                if(!state.selectedOpponent!!.isPlayable){
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackBar(
                            message = "Χρειάζεσαι 2 νίκες για να κάνεις μάχη με τον ${state.selectedOpponent!!.name}!"
                        ))
                    }
                    return
                }

                val totalCardsSelectedDeck = state.savedCardList.filter { it.deckNumber == state.selectedDeck }.size

                if(totalCardsSelectedDeck < TOTAL_DECK_CARDS_GLOBAL){
                    viewModelScope.launch {
                        _uiEvent.send(UiEvent.ShowSnackBar(
                            message = "Χρειάζεσαι $TOTAL_DECK_CARDS_GLOBAL κάρτες και έχεις $totalCardsSelectedDeck!"
                        ))
                    }
                    return
                }
            }
        }
    }

    private fun getCardsFromDb() {
        allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { savedCardList ->
            state = state.copy(
                savedCardList = savedCardList
            )
        }.launchIn(viewModelScope)
    }
}