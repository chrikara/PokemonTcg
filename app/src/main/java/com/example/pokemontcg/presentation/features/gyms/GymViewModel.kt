package com.example.pokemontcg.presentation.features.gyms

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.toNumberString
import com.example.pokemontcg.domain.use_cases.AllGymOpponentsUseCases
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.domain.use_cases.FilterOutDeckUseCase
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL
import com.example.pokemontcg.util.UiEvent
import com.example.pokemontcg.util.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GymViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val allGymOpponentsUseCases: AllGymOpponentsUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    var state by mutableStateOf<GymState>(GymState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    init {
        val selectedGymName = savedStateHandle.get<String>("gymName") ?: "Pewter"

        getCardsFromDb()
        getOpponentsFromDb(selectedGymName)

    }

    private fun getOpponentsFromDb(selectedGymName : String) {

            allGymOpponentsUseCases.getAllGymOpponentsUseCase().onEach { allOpponents->
                if(allOpponents.isEmpty()){
                    return@onEach
                }

                delay(20L)
                val opponentsForSelectedGym = allOpponents.filter { it.gymName == selectedGymName }

                state = state.copy(
                    opponentsForThisGym = opponentsForSelectedGym,
                    selectedOpponent = opponentsForSelectedGym.first(),
                    isLoadingDb = false
                )
                onEvent(GymEvent.OnErrorTextChange)
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: GymEvent){
        when(event){
            is GymEvent.OnNextOpponent ->{
                if(state.selectedOpponent == state.opponentsForThisGym.last()){
                    return
                }

                val currentOpponentIndex = state.opponentsForThisGym.indexOf(state.selectedOpponent)

                state = state.copy(
                    selectedOpponent = state.opponentsForThisGym[currentOpponentIndex + 1],
                    isPreviousOpponentEnabled = true,
                    isNextOpponentEnabled = if(state.opponentsForThisGym[currentOpponentIndex + 1] == state.opponentsForThisGym.last()) false else true
                )
            }
            GymEvent.OnPreviousOpponent -> {
                if(state.selectedOpponent == state.opponentsForThisGym.first()){
                    return
                }

                val currentOpponentIndex = state.opponentsForThisGym.indexOf(state.selectedOpponent)



                state = state.copy(
                    selectedOpponent = state.opponentsForThisGym[currentOpponentIndex - 1],
                    isPreviousOpponentEnabled = if(state.opponentsForThisGym[currentOpponentIndex-1] == state.opponentsForThisGym.first()) false else true,
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
                viewModelScope.launch {
                    _uiEvent.send(
                        UiEvent.Navigate(
                        route = Screen.Game.route
                                + "/${state.selectedDeck.toNumberString()}"
                                + "/${state.selectedOpponent!!.name}"
                        )
                    )
                }
            }

            is GymEvent.OnErrorTextChange -> {
                if(state.opponentsForThisGym.count{ it.isBeaten } < 2 && state.selectedOpponent!!.isBoss){
                    state = state.copy(
                        messageError = "${state.selectedOpponent!!.name}: Χρειάζεσαι 2 νίκες για να με πολεμήσεις μικρέ!",
                        isButtonEnabled = false
                    )
                    return
                }

                val totalCardsSelectedDeck = state.savedCardList.filter { it.deckNumber == state.selectedDeck }.size
                println(totalCardsSelectedDeck)
                if(totalCardsSelectedDeck < TOTAL_DECK_CARDS_GLOBAL){
                        state = state.copy(
                            messageError = "Χρειάζεσαι $TOTAL_DECK_CARDS_GLOBAL κάρτες και έχεις $totalCardsSelectedDeck!",
                            isButtonEnabled = false
                        )
                    return
                }

                state = state.copy(
                    messageError = "",
                    isButtonEnabled = true
                )


            }
        }
    }


    private fun getCardsFromDb() {
        allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { savedCardList ->
            state = state.copy(
                savedCardList = savedCardList,
                selectedDeck = defaultDecks.first()

            )
        }.launchIn(viewModelScope)
    }
}