package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.EnergyCard
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonType
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases.PlayerTurnUseCases
import com.example.pokemontcg.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamePlayerTurnViewModel @Inject constructor(
    private val playerTurnUseCases: PlayerTurnUseCases
) : ViewModel() {

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
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

            is PlayerTurnEvent.OnSelectedCardIndex -> {
                state = state.copy(
                    selectedCardIndex = event.index
                )
            }

            is PlayerTurnEvent.OnClickHand -> {
                if(event.currentHand.isEmpty()){
                    viewModelScope.launch {
                        _uiEvent.send(
                            UiEvent.ShowSnackBar(
                                message = "You have an empty hand!"
                            )
                        )
                    }
                }else{
                    event.onShowHand()
                }
            }

            is PlayerTurnEvent.OnClickSelectedCardBasicPokemon -> {
                val benchPokemon = event.viewModelGame.state.player.benchPokemon
                val currentHand = event.viewModelGame.state.player.currentHand

                when(event.gameCard){
                    is PokemonCard ->{
                        when(event.gameCard.pokemonType){
                            PokemonType.Basic -> {
                                if(benchPokemon.size < 3){

                                    benchPokemon.add(event.gameCard)
                                    currentHand.remove(event.gameCard)


                                    println("State index" + state.selectedCardIndex)

                                    state = state.copy(

                                        /*
                                        Same as ChooseBench, we modify selectedInde if card removed
                                        was last.
                                         */
                                        selectedCardIndex =
                                        if(state.selectedCardIndex == currentHand.size
                                            && currentHand.isNotEmpty())
                                            currentHand.lastIndex
                                        else state.selectedCardIndex,

                                        currentHandSize = currentHand.size
                                        /*
                                        This is just to update state so we can see changes
                                         */

                                    )
                                }
                            }
                            PokemonType.Stage1 -> {}
                            PokemonType.Stage2 -> {}
                        }
                    }
                    else -> Unit
                }
            }
        }

    }
}