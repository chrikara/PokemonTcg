package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemontcg.domain.model.Evolution
import com.example.pokemontcg.domain.model.cardinfo.EnergyInfoCard
import com.example.pokemontcg.domain.model.cardinfo.PokeInfoCard
import com.example.pokemontcg.domain.model.cardinfo.SuperType
import com.example.pokemontcg.presentation.features.createdecks.use_cases.AllMyDeckUseCases
import com.example.pokemontcg.util.Pokedex
import com.example.pokemontcg.util.Pokedex.getKeyByPokemonName
import com.example.pokemontcg.util.Pokedex.pokedexBaseIdtoNameHash
import com.example.pokemontcg.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf
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
        println(pokeId)
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

                        val evolvesFromName = result.data?.evolvesFrom
                        val initialName = result.data?.name!!
                        val evolvesToName = result.data.evolvesTo?.get(0)

                        val evolution = Evolution.returnEvolution(evolvesFromName,evolvesToName)


                        when(evolution){
                            is Evolution.None -> state = state.copy(evolution = Evolution.None(initial = evolveUrl(initialName)))
                            is Evolution.Both -> {
                                val baseId = getKeyByPokemonName(pokedexBaseIdtoNameHash, evolvesToName!! ) ?: kotlin.run {
                                    state = state.copy(
                                        evolution = Evolution.From(
                                            from = evolveUrl(evolvesFromName!!),
                                            initial = evolveUrl(initialName)
                                        )
                                    )
                                    return@flatMapConcat flowOf(Unit)
                                }
                                state = state.copy(evolution = Evolution.Both(

                                    from = evolveUrl(evolvesFromName!!),
                                    to = evolveUrl(evolvesToName),
                                    initial = evolveUrl(initialName)
                                ))
                            }
                            is Evolution.From ->{



                                val baseId = getKeyByPokemonName(pokedexBaseIdtoNameHash, evolvesFromName!! )!!
                                allMyDeckUseCases.getPokemonInfoFromAPIUseCase(baseId).onEach { result2->
                                    when (result2){
                                        is Resource.Success ->{
                                            val evolvesFromName2 = result2.data?.evolvesFrom
                                            val evolvesToName2 = result2.data?.evolvesTo?.get(0)
                                            val evolution2 = Evolution.returnEvolution(
                                                evolvesFromName2,
                                                evolvesToName2
                                            )

                                            when(evolution2){

                                                is Evolution.Both -> state = state.copy(evolution = Evolution.Both(
                                                    from = evolveUrl(evolvesFromName2!!),
                                                    initial = evolveUrl(result2.data.name),
                                                    to = evolveUrl(evolvesToName2!!),
                                                )
                                                )
                                                else -> state = state.copy(evolution = Evolution.From(
                                                    from =  evolveUrl(result2.data?.name!!),
                                                    initial = evolveUrl(initialName)
                                                ))
                                            }
                                        }
                                        else -> {}
                                    }
                                }.launchIn(viewModelScope)
                            }

                            is Evolution.To -> {
                                val baseId = getKeyByPokemonName(pokedexBaseIdtoNameHash, evolvesToName!! ) ?: kotlin.run {
                                    state = state.copy(evolution = Evolution.None(
                                        initial = evolveUrl(initialName))
                                    )
                                   return@flatMapConcat flowOf(Unit)
                                }
                                allMyDeckUseCases.getPokemonInfoFromAPIUseCase(baseId).onEach { result2 ->
                                    when (result2){
                                        is Resource.Success ->{
                                            val evolvesFromName2 = result2.data?.evolvesFrom
                                            val evolvesToName2 = result2.data?.evolvesTo?.get(0)
                                            val evolution2 = Evolution.returnEvolution(
                                                evolvesFromName2,
                                                evolvesToName2
                                            )



                                            when(evolution2){
                                                is Evolution.Both -> {
                                                    evolveUrl(evolvesToName2!!) ?: kotlin.run {
                                                        state = state.copy(
                                                            evolution = Evolution.To(
                                                                initial = evolveUrl(evolvesFromName2!!),
                                                                to = evolveUrl(result2.data.name),
                                                            )
                                                        )
                                                        return@onEach
                                                    }

                                                    state = state.copy(evolution = Evolution.Both(
                                                        from = evolveUrl(evolvesFromName2!!),
                                                        initial = evolveUrl(result2.data.name),
                                                        to = evolveUrl(evolvesToName2!!),
                                                    )
                                                    )
                                                }
                                                else -> state = state.copy(evolution = Evolution.To(
                                                    initial = evolveUrl(initialName),
                                                    to =  evolveUrl(result2.data?.name!!),
                                                    )
                                                )
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


private fun evolveUrl(pokeName : String) : String?{

    val dexString = getKeyByPokemonName(Pokedex.pokedexNationaltoNameHash,pokeName ) ?: return null

    return when(dexString.length){
        1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00$dexString.png"
        2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0$dexString.png"
        else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dexString.png"
    }
}
//private fun evolveUrl(dexString: String) : String{
//    return when(dexString.length){
//        1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00$dexString.png"
//        2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0$dexString.png"
//        else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dexString.png"
//    }
//}