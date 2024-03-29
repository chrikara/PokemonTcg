package com.example.pokemontcg.presentation.features.welcome

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.defaults.defaultOpponents
import com.example.pokemontcg.domain.preferences.Preferences
import com.example.pokemontcg.domain.use_cases.AllGymOpponentsUseCases
import com.example.pokemontcg.domain.use_cases.AllMyDeckUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val allGymOpponentsUseCases: AllGymOpponentsUseCases,
    private val allMyDeckUseCases: AllMyDeckUseCases
) : ViewModel() {

    var state by mutableStateOf<WelcomeState>(
        WelcomeState(
        hasAlreadyGame = preferences.loadHasAldreadGame()
        )
    )
        private set


    fun onStartNewGame(){

        preferences.saveHasAlreadyGame(true)
        state = state.copy(
            hasAlreadyGame = preferences.loadHasAldreadGame()
        )
    }

    fun onDialog(){
        state = state.copy(
            isDialogShown = !state.isDialogShown
        )
    }

    fun deleteAllDataAndInsertDefaultOpponentsInDb(){

        viewModelScope.launch {



            allGymOpponentsUseCases.deleteOpponentFromDbUseCase()
            allMyDeckUseCases.deleteAllPokemonCardsUseCase()
            defaultOpponents.forEach{allGymOpponentsUseCases.insertGymOpponentUseCase(it)}

        }



    }















}