package com.example.pokemontcg.presentation.features.game.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun ShuffleDeckGameScreen(viewModel: GameViewModel, secondsToStart : Int) {
    var currentTime by remember {
        mutableStateOf(secondsToStart)
    }

    var text by remember {
        mutableStateOf("Εδώ θα βάλω animation με shuffle")
    }

    LaunchedEffect(key1 = true){
        while (true){
            println("Arxi tou loop")
            while(currentTime!=0){
                currentTime-=1
                delay(1000L)
            }
            viewModel.onEvent(GameEvent.OnShuffleDeck(viewModel.state.player))
            viewModel.onEvent(GameEvent.OnShuffleDeck(viewModel.state.opponent))
            viewModel.onEvent(GameEvent.OnGive7CardsToEachPlayer)

            if(viewModel.state.currentState == GameState.GameSealedClass.START){
                currentTime = secondsToStart
                text = "Ένας από τους παίχτες δεν είχε βασικά Pokemon"
            }else{
                break
            }
        }

    }

    Box(modifier = Modifier.fillMaxSize()){
        Text(modifier = Modifier
            .align(Alignment.Center),
            text = text + "\n$currentTime",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
    }
}