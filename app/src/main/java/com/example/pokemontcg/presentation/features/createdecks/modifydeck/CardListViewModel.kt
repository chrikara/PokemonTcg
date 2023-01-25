package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.domain.use_cases.GetCardsUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.presentation.features.createdecks.use_cases.GetPokemonFromDeckUseCase
import com.example.pokemontcg.presentation.features.createdecks.use_cases.InsertPokemonToDeckUseCase
import com.example.pokemontcg.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val getCardsUseCase: GetCardsUseCase,
    private val allMyDeckUseCases: AllMyDeckUseCases
) : ViewModel() {

    var state by mutableStateOf<CardListState>(CardListState())
        private set

    private var getCardsForOneDeckJob: Job? = null

    init {
        getAllAvailableCardsFromAPI()
    }
    fun getCardsForOneDeck(deckNumber : DeckNumber) {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = allMyDeckUseCases.getPokemonFromDeckUseCase().onEach { cardsSaved ->

            state = state.copy(
                savedCardList = cardsSaved.filter {  it.deckNumber == deckNumber}
            )

        }.launchIn(viewModelScope)
    }

    fun deletePokemonFromDeck(cardOverview: CardOverview){
        viewModelScope.launch {
            if(cardOverview.id in state.savedCardList.map { it.pokemonId }){
                val cardSaved = state.savedCardList.find { it.pokemonId == cardOverview.id }!!
                allMyDeckUseCases.deletePokemonFromDeckUseCase(cardSaved)
            }

        }
    }
    fun insertPokemonToDeck(
        deckToInsert : Int,
        card : CardOverview
    ){
        viewModelScope.launch {
            when{
                state.savedCardList.size <60 ->{
                    insertPokemonToDeck(
                        deckToInsert = deckToInsert,
                        card = card,
                        allMyDeckUseCases = allMyDeckUseCases
                    )
                }
                else -> Unit
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
                    state = state.copy(
                        error = result.message ?: "An error occured",
                        isLoading = false
                    )                }
                is Resource.Loading -> {
                    state = state.copy(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}

private suspend fun insertPokemonToDeck(
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