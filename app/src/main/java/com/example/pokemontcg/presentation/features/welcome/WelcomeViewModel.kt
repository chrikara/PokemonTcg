package com.example.pokemontcg.presentation.features.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.domain.preferences.Preferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var state by mutableStateOf<WelcomeState>(
        WelcomeState(
        hasAlreadyGame = preferences.loadHasAldreadGame()
        )
    )
        private set

    init {
    }

    fun onStartNewGame(){
        preferences.saveHasAlreadyGame(true)
        state = state.copy(
            hasAlreadyGame = preferences.loadHasAldreadGame()
        )
    }

}