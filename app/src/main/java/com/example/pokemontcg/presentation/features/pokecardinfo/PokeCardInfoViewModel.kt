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
import com.example.pokemontcg.domain.model.Evolution
import com.example.pokemontcg.domain.model.cardinfo.EnergyInfoCard
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.domain.model.cardinfo.SuperType
import com.example.pokemontcg.domain.model.cardinfo.TrainerInfoCard
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.Pokedex
import com.example.pokemontcg.util.Pokedex.getKeyByPokemonName
import com.example.pokemontcg.util.Pokedex.pokedexBaseIdtoNameHash
import com.example.pokemontcg.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.flatMap
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn

import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
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


    @OptIn(FlowPreview::class)
    private fun getPokeCardInfoByPokemonIdFromAPI(pokemonId : String){

       val flow =  allMyDeckUseCases.getPokemonInfoFromAPIUseCase(pokemonId).onEach { result->
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
                           println("MPIKE 1")
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
               else -> Unit
           }
       }


            flow.flatMapConcat {result ->
                when(result){
                    is Resource.Success ->{
                        val evolution = Evolution.returnEvolution(result.data?.evolvesFrom,result.data?.evolvesTo?.get(0))

                        println(evolution)
                        when(evolution){
                            is Evolution.None -> state = state.copy(evolution = Evolution.None)
                            is Evolution.Both -> state = state.copy(evolution = Evolution.Both)
                            is Evolution.From ->{
                                val evolvesFrom = result.data?.evolvesFrom

                                val baseId = getKeyByPokemonName(pokedexBaseIdtoNameHash, evolvesFrom!! )!!
                                allMyDeckUseCases.getPokemonInfoFromAPIUseCase(baseId).onEach {
                                    when (it){
                                        is Resource.Success ->{
                                            val evolution2 = Evolution.returnEvolution(it.data?.evolvesFrom,it.data?.evolvesTo?.get(0))

                                            when(evolution2){
                                                is Evolution.Both -> state = state.copy(evolution = Evolution.Both)
                                                else -> state = state.copy(evolution = Evolution.From)
                                            }
                                        }
                                        else -> {}
                                    }
                                }.launchIn(viewModelScope)
                            }

                            is Evolution.To -> {
                                val evolvesTo = result.data?.evolvesTo?.get(0)
                                val baseId = getKeyByPokemonName(pokedexBaseIdtoNameHash, evolvesTo!!)?: kotlin.run {
                                    state = state.copy(evolution = Evolution.None)
                                   return@flatMapConcat flowOf(Unit)
                                }
                                allMyDeckUseCases.getPokemonInfoFromAPIUseCase(baseId).onEach {
                                    when (it){
                                        is Resource.Success ->{
                                            val evolution2 = Evolution.returnEvolution(it.data?.evolvesFrom,it.data?.evolvesTo?.get(0))

                                            when(evolution2){
                                                is Evolution.Both -> state = state.copy(evolution = Evolution.Both)
                                                else -> state = state.copy(evolution = Evolution.To)
                                            }
                                        }
                                        else -> {}
                                    }
                                }.launchIn(viewModelScope)
                            }
                        }
                        flowOf(Unit)
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message ?: "An error occured",
                            isLoading = false
                        )
                        flowOf(Unit)
                    }
                    is Resource.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                        flowOf(Unit)
                    }
            }
        }.launchIn(viewModelScope)
    }
}