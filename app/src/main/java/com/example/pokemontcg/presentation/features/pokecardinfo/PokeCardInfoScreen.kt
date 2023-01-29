package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemontcg.presentation.features.pokecardinfo.components.PokeCardInfo

@Composable
fun PokeCardInfoScreen(
    navController: NavController,
    viewModel : PokeCardInfoViewModel = hiltViewModel()
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFD2DBE4)))
    {

        if(viewModel.state.pokeInfoCard!=null)
            PokeCardInfo(
                pokeInfoCard = viewModel.state.pokeInfoCard!!,
                navController = navController,
                initialSize = viewModel.state.initialAnimationSize,
                onSize = { dp ->
                    viewModel.onChangeSize(dp)
                }
            )

        if(viewModel.state.isLoading)
            CircularProgressIndicator(modifier = Modifier
                .size(100.dp)
                .align(Alignment.Center))

        if(viewModel.state.error!="")
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                text = viewModel.state.error,
                textAlign = TextAlign.Center)
        }
}