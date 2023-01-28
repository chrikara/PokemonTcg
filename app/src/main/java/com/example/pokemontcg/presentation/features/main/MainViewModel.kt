package com.example.pokemontcg.presentation.features.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.repository.PokemonCardsRepository
import com.example.pokemontcg.util.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PokemonCardsRepository
) : ViewModel()

{
    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()



    }




