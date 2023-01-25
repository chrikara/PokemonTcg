package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PokeCardInfoScreen(
    pokeId : String,
    viewModel : PokeCardInfoViewModel = hiltViewModel()
) {


    viewModel.getPokeCardInfoByPokemonIdFromAPI(pokeId)
    println(viewModel.state)


}