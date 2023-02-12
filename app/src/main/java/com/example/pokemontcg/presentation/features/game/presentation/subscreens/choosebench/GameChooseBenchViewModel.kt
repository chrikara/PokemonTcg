package com.example.pokemontcg.presentation.features.game.presentation.subscreens.choosebench

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameChooseBenchViewModel @Inject constructor(
    private val repository: PokemonCardsRepository
) : ViewModel() {

    var state by mutableStateOf(GameChooseBenchState())
        private set



    fun onEvent(event: GameChooseBenchEvent) {
        when(event){
            is GameChooseBenchEvent.OnCardInfoImage ->{
                state = state.copy(
                    cardInfoImage = event.imageUrl
                )
            }
            is GameChooseBenchEvent.OnItemSelected -> {
                state = state.copy(
                    selectedCardIndex = event.index
                )
            }
        }
    }
}