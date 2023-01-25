package com.example.pokemontcg.presentation.features.createdecks.newdeck

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.use_cases.GetCardsUseCase
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
    private val getPokemonFromDeckUseCase: GetPokemonFromDeckUseCase,
    private val insertPokemonToDeckUseCase: InsertPokemonToDeckUseCase
) : ViewModel() {

    var state by mutableStateOf<CardListState>(CardListState())
        private set

    private var getCardsForOneDeckJob: Job? = null

    init {
        getCardsForOneDeck()
        getAllAvailableCardsFromAPI()
    }
    fun getCardsForOneDeck() {
        getCardsForOneDeckJob?.cancel()
        getCardsForOneDeckJob = getPokemonFromDeckUseCase().onEach { cardsSaved ->

            state = state.copy(
                savedCardList = cardsSaved
            )

        }.launchIn(viewModelScope)
    }
    fun insertPokemonToDeck(card : CardOverview){
        viewModelScope.launch {
            when{
                state.savedCardList.size <60 ->{
                    insertPokemonToDeck(
                        insertPokemonToDeckUseCase,
                        card
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

private suspend fun insertPokemonToDeck(insertPokemonToDeckUseCase: InsertPokemonToDeckUseCase,card: CardOverview){
    insertPokemonToDeckUseCase(
        deckNumber = 1,
        pokemonId = card.id,
        pokemonName = card.name,
        pokemonImageUrl = card.imgString,
        nationalDex = card.nationalDex?: 152
    )
}