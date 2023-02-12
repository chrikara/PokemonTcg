package com.example.pokemontcg.presentation.features.game.presentation.subscreens.check

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameCheckViewModel @Inject constructor(
    private val repository: PokemonCardsRepository
)  : ViewModel(){

    var state by mutableStateOf(GameCheckState())
        private set

    fun onCardInfoImageUrl(imageUrl : String){
        state = state.copy(
            imageUrlCardClicked = imageUrl
        )
    }

}