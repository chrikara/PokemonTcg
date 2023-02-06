package com.example.pokemontcg.presentation.features.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.GymOpponent
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.domain.use_cases.AllGymOpponentsUseCases
import com.example.pokemontcg.util.UiEvent
import com.example.pokemontcg.util.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokemonCardsRepository,
    private val allGymOpponentsUseCases: AllGymOpponentsUseCases
) : ViewModel()

{
    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getOpponentsFromDb: Job? = null

    init {
        viewModelScope.launch {
            delay(150) // Because it somtimes crashes
            getAllGymOpponents()
        }

    }

    var state by mutableStateOf<MainState>(MainState())
        private set

    fun onNextAllDecksClick(){
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(Screen.AllDecks.route))
        }
    }

    fun onNextGymClick(gymName : String){
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(Screen.Gym.route+ "/${gymName}"))
        }
    }

    fun getAllGymOpponents() {
        getOpponentsFromDb?.cancel()
        getOpponentsFromDb = allGymOpponentsUseCases.getAllGymOpponentsUseCase().onEach {

            state = state.copy(
                opponentsList = it
            )
        }.launchIn(viewModelScope)
    }



}




