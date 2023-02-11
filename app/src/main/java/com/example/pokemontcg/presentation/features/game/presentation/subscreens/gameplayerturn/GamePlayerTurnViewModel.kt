package com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn.use_cases.PlayerTurnUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamePlayerTurnViewModel @Inject constructor(
    private val playerTurnUseCases: PlayerTurnUseCases
) : ViewModel() {

    var state by mutableStateOf(GamePlayerTurnState())
        private set


    fun onEvent(event: PlayerTurnEvent) {
        when (event) {
            is PlayerTurnEvent.OnAttackButtonTriggered -> {
                state = state.copy(
                    isAttackButtonEnabled = !state.isAttackButtonEnabled
                )
            }

            is PlayerTurnEvent.OnCardInfoImage -> {
                state = state.copy(
                    cardInfoImage = event.imageUrl
                )
            }
        }

    }
}