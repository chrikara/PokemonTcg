package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Data
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn

import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokeCardInfoViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases
) : ViewModel(){

    var state by mutableStateOf<CardOverview?>(null)
        private set


    fun getPokeCardInfoByPokemonIdFromAPI(pokemonId : String){
       allMyDeckUseCases.getPokemonInfoFromAPIUseCase(pokemonId).onEach { result->
           when(result){
               is Resource.Success ->{

                   val data = result.data!!
                   state = data
               }

               else -> {}
           }
       }.launchIn(viewModelScope)

    }
}