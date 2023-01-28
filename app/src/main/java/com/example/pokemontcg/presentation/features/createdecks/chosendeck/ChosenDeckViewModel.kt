package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.use_cases.FilterOutDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.UiEvent
import com.example.pokemontcg.util.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChosenDeckViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val filterOutDeckUseCase: FilterOutDeckUseCase
) : ViewModel() {

    var state by mutableStateOf<ChosenDeckState>(ChosenDeckState())
        private set

    private var getCardsForOneDeckJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()




    fun onEvent(event : ChosenDeckEvent){
        when(event){
            is ChosenDeckEvent.GetAllCardsFromRoom ->{
                getCardsForOneDeck(deckNumber = event.deckNumber)
            }

            is ChosenDeckEvent.DeleteCardFromRoom -> {
                deleteCardFromDeck(event.cardSaved)
            }
            is ChosenDeckEvent.ShowCardInfo -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.PokeCardInfo.route + "/${event.cardSaved.pokemonId}"))
                }
            }
            is ChosenDeckEvent.OnModifyDeckClick -> {
                viewModelScope.launch {
                    _uiEvent.send(UiEvent.Navigate(Screen.DeckModify.route + "/${event.deckNumber}"))
                }
            }

            is ChosenDeckEvent.OnChangeGaugeRatio -> {
                state = state.copy(
                    gaugeRatio = event.ratio
                )
            }
        }
    }


    fun getCardsForOneDeck(deckNumber: DeckNumber) {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->

        state = state.copy(
            cardsSaved = filterOutDeckUseCase(
                allCardsInRoom = cardsSaved,
                deckNumber = deckNumber
            )
        )

        }.launchIn(viewModelScope)
    }

    fun deleteCardFromDeck(cardSaved: CardSaved){
        viewModelScope.launch {
            allMyDeckUseCases.deletePokemonFromDeckUseCase(cardSaved)
        }
    }
}