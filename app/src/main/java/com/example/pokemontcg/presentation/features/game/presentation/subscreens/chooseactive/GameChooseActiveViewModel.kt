package com.example.pokemontcg.presentation.features.game.presentation.subscreens.chooseactive

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameChooseActiveViewModel @Inject constructor(
    private val repository: PokemonCardsRepository
) : ViewModel(){

    var state by mutableStateOf(GameChooseActiveState())
        private set

    fun onItemSelected(index : Int) {
        state = state.copy(
            selectedCardIndex = index
        )
    }

    fun onCardInfoImage(imageUrl : String) {
        state = state.copy(
            cardInfoImage = imageUrl
        )
    }
}