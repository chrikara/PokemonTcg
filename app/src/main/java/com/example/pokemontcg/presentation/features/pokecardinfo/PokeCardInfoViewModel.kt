package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.data.remote.api.dto.cardoverviewdto.Data
import com.example.pokemontcg.domain.model.CardOverview
import com.example.pokemontcg.domain.model.cardinfo.EnergyInfoCard
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.domain.model.cardinfo.SuperType
import com.example.pokemontcg.domain.model.cardinfo.TrainerInfoCard
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn

import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokeCardInfoViewModel @Inject constructor(
    private val allMyDeckUseCases: AllMyDeckUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    var state by mutableStateOf(PokeCardInfoStateScreen())
        private set

    init {
        val pokeId = savedStateHandle.get<String>("pokeId") ?: "base1-1"
        getPokeCardInfoByPokemonIdFromAPI(pokeId)
    }
    fun onChangeSize(size : Dp){
        state = state.copy(
            initialAnimationSize = size
        )
    }

    private fun getPokeCardInfoByPokemonIdFromAPI(pokemonId : String){

        allMyDeckUseCases.getPokemonInfoFromAPIUseCase(pokemonId).onEach { result->
            when(result){
               is Resource.Success ->{
                   val data = result.data!!
                   state = state.copy(isLoading = false)


                   when(SuperType.fromString(data.supertype)){
                       is SuperType.Pokemon->{
                           state = state.copy(
                               pokeInfoCard = PokeInfoCard(
                                   name = data.name,
                                   imageUrl = data.images.large,
                                   nationalDex = data.nationalPokedexNumbers?.get(0),
                                   description = data.flavorText,
                                   attacks = data.attacks,
                                   types = data.types?.get(0),
                                   weakness = data.weaknesses?.get(0)?.type,
                                   evolvesFrom = data.evolvesFrom,
                                   evolvesTo = data.evolvesTo?.get(0),
                                   hp = data.hp,
                                   retreatCost = data.retreatCost,
                                   resistanceType = data.resistances?.get(0)?.type,
                                   resistanceValue = data.resistances?.get(0)?.value
                               )
                           )
                       }
                       SuperType.Energy -> {
                           state = state.copy(
                               energyInfoCard = EnergyInfoCard(
                                   name = data.name,
                                   imageUrl = data.images.large
                               )
                           )
                       }
                       else -> Unit
                   }

               }
               is Resource.Error -> {
                   state = state.copy(
                       error = result.message ?: "An error occured",
                       isLoading = false
                   )
               }
               is Resource.Loading -> {
                   state = state.copy(
                       isLoading = true
                   )
               }
           }
       }.launchIn(viewModelScope)
    }
}