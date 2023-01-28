package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.use_cases.FilterOutDeckUseCase
import com.example.pokemontcg.domain.use_cases.GetCardsUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.Resource
import com.example.pokemontcg.util.TOTAL_DECK_CARDS_GLOBAL
import com.example.pokemontcg.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val filterOutDeckUseCase: FilterOutDeckUseCase
) : ViewModel() {

    var state by mutableStateOf(CardListState())
        private set

    var count = 0
    private var getCardsForOneDeckJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        getAllAvailableCardsFromAPI()
    }


    fun getCardsForOneDeck(deckNumber : DeckNumber) {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->

            state = state.copy(
                savedCardList = filterOutDeckUseCase(
                    allCardsInRoom = cardsSaved,
                    deckNumber = deckNumber
                )
            )

        }.launchIn(viewModelScope)
    }

    fun deletePokemonFromDeck(cardOverview: CardOverview){
        viewModelScope.launch {

            val pokemonIdsInCurrentDeck = state.savedCardList.map { it.pokemonId }

            if(cardOverview.id in pokemonIdsInCurrentDeck){
                val cardSelected = state.savedCardList.find { it.pokemonId == cardOverview.id }!!
                allMyDeckUseCases.deletePokemonFromDeckUseCase(cardSelected)
            }

        }
    }

    fun onChangeRatio(ratio : Float){
        state = state.copy(
            gaugeRatio = ratio
        )
    }
    fun insertPokemonToDeck(
        deckToInsert : Int,
        card : CardOverview,
        snackbarHostState: SnackbarHostState
    ){
        viewModelScope.launch {
                if(state.savedCardList.size  <  TOTAL_DECK_CARDS_GLOBAL){
                    insertToDeck(
                        deckToInsert = deckToInsert,
                        card = card,
                        allMyDeckUseCases = allMyDeckUseCases
                    )
                }
                else{
                    snackbarHostState.currentSnackbarData?.dismiss()
                    _uiEvent.send(UiEvent.ShowSnackBar(
                        message = "Έχεις ήδη $TOTAL_DECK_CARDS_GLOBAL κάρτες!"
                    ))
                }
            }
        }


    fun getAllAvailableCardsFromAPI(){
        getCardsUseCase().onEach {result ->
            when(result){
                is Resource.Success -> {
                    var newResult = result.data!!

                    newResult = newResult.map { card->
                        if(card.nationalDex==null){
                            card.copy(nationalDex = 152)
                        } else card
                    }

                    state = state.copy(
                        cardList = newResult.sortedBy { it.nationalDex },
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    println("mpike error $count")

                    state = state.copy(
                        error = result.message ?: "An error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
                    println("mpike loading $count")

                    state = state.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

private suspend fun insertToDeck(
    allMyDeckUseCases: AllMyDeckUseCases,
    card: CardOverview,
    deckToInsert : Int
){
    allMyDeckUseCases.insertPokemonToDeckUseCase(
        deckNumber = deckToInsert,
        pokemonId = card.id,
        pokemonName = card.name,
        pokemonImageUrl = card.imgString,
        nationalDex = card.nationalDex?: 152
    )
}

