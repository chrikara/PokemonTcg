package com.example.pokemontcg.presentation.carddetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.data.remote.api.dto.toCardOverViewList
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardListViewModel @Inject constructor(
    private val repository: PokemonCardsRepository
) : ViewModel() {

    var state by mutableStateOf<CardListState>(CardListState())
        private set

    init {
        getCoinList()
    }
    fun getCoinList(){
        viewModelScope.launch {

            val cardsDto = repository.getCards().data


            val cards = cardsDto.map { it.toCardOverViewList() }

            state = state.copy(
                cardList = cards
            )
        }


    }
}