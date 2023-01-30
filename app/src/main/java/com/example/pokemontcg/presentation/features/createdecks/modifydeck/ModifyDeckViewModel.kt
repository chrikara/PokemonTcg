package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.model.cardinfo.SuperType
import com.example.pokemontcg.domain.model.toInt
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
class ModifyDeckViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val allMyDeckUseCases: AllMyDeckUseCases,
    private val filterOutDeckUseCase: FilterOutDeckUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(ModifyDeckState())
        private set

    var cardListFromAPIWithAllCards : List<CardOverview> = emptyList()

    private var getCardsForOneDeckJob: Job? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        val deckNumber = DeckNumber.fromInt(savedStateHandle.get<Int>("deckNumber") ?: 1)
        getCardsForOneDeck(deckNumber)
        getAllAvailableCardsFromAPI()
    }

    fun onEvent(event : ModifyDeckEvent){
        when(event){
            is ModifyDeckEvent.OnChangedGaugeRatio -> {
                onChangeRatio(event.ratio)
            }

            is ModifyDeckEvent.OnDeletePokemonFromDeck -> {
                deletePokemonFromDeck(cardOverview = event.cardOverview)
            }
            is ModifyDeckEvent.OnInsertToChosenDeck -> {
                insertPokemonToDeck(
                    deckNumber = event.deck,
                    card = event.cardOverview,
                    snackbarHostState = event.snackbarHostState
                )
            }

            is ModifyDeckEvent.OnFocused -> {
                state = state.copy(
                    isHintVisible = !event.focusState.isFocused && state.query.isBlank()
                )
            }
            is ModifyDeckEvent.OnSearch -> {

                if(event.query.isBlank()){
                    state = state.copy(
                        cardList = cardListFromAPIWithAllCards
                    )
                } else{
                    state = state.copy(
                        cardList = cardListFromAPIWithAllCards.filter {card->
                            card.name.lowercase().contains(event.query.lowercase())
                        }
                    )
                }


            }
            is ModifyDeckEvent.OnTextFieldChange -> {
                state = state.copy(
                    query = event.query
                )
            }

            is ModifyDeckEvent.OnSearchBarExpanded -> {
                state = state.copy(
                    isSearchBarExpanded = !state.isSearchBarExpanded
                )

                if(!state.isSearchBarExpanded){
                    state = state.copy(
                        query = "",
                        cardList = cardListFromAPIWithAllCards
                    )
                }
            }
        }
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
        deckNumber: DeckNumber ,
        card : CardOverview,
        snackbarHostState: SnackbarHostState
    ){
        viewModelScope.launch {
                if(state.savedCardList.size  <  TOTAL_DECK_CARDS_GLOBAL){
                    insertToDeck(
                        deckNumber = deckNumber,
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
                            card.copy(nationalDex = 1000)
                        } else card
                    }
                        .filter { card -> card.superType != SuperType.Trainer }
                        .sortedBy { it.nationalDex }

                    cardListFromAPIWithAllCards = newResult

                    state = state.copy(
                        cardList = newResult,
                        isLoading = false,
                        error = ""
                    )
                }

                is Resource.Error -> {
                    state = state.copy(
                        error = result.message ?: "An error occured",
                        isLoading = false
                    )
                }
                is Resource.Loading -> {
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
    deckNumber: DeckNumber
){
    allMyDeckUseCases.insertPokemonToDeckUseCase(
        deckNumber = deckNumber.toInt(),
        pokemonId = card.id,
        pokemonName = card.name,
        pokemonImageUrl = card.imgString,
        nationalDex = card.nationalDex?: 152
    )
}

