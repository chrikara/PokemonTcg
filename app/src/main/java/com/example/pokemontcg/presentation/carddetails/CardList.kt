package com.example.pokemontcg.presentation.carddetails

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemontcg.data.remote.api.dto.Data

@Composable
fun CardList(
    viewModel: CardListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    viewModel.getCoinList()


}

