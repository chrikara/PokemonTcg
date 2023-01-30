package com.example.pokemontcg.presentation.features.pokecardinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import com.example.pokemontcg.ui.theme.LocalSpacing

@Composable
fun PokeCardInfoScreen(
    navController: NavController,
    viewModel : PokeCardInfoViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    Box(modifier = Modifier
        .fillMaxSize()

        .background(Color(0xFFD2DBE4))
        .padding(spacing.paddingSmall)
    )
    {

        println(viewModel.state)

        if(viewModel.state.pokeInfoCard!=null)
            PokeCardInfo(
                pokeInfoCard = viewModel.state.pokeInfoCard!!,
                navController = navController,
                initialSize = viewModel.state.initialAnimationSize,
                onSize = { dp ->
                    viewModel.onChangeSize(dp)
                },
                evolution = viewModel.state.evolution
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
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.background
            )
        }
}