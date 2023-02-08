@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.example.pokemontcg.presentation.features.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.gyms.GymEvent
import com.example.pokemontcg.ui.theme.BlueAlpha80
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {

    val state = viewModel.state

    if(state.currentState == GameState.GameEnums.LOADING){
        LoadingScreen()
    }

    if(state.currentState == GameState.GameEnums.START){
        GameTimer(secondsToStart = 1){
            viewModel.onEvent(GameEvent.OnShuffleDeck(state.player))
            viewModel.onEvent(GameEvent.OnShuffleDeck(state.opponent))
            viewModel.onEvent(GameEvent.OnGive7CardsToEachPlayer)
        }
    }

    if(state.currentState == GameState.GameEnums.PLAYER_TURN){
        GameMainScreen(nationalDex = 2)
    }



}

@Composable
fun GameTimer(secondsToStart : Int, onFinish : () -> Unit) {
    var currentTime by remember {
        mutableStateOf(secondsToStart)
    }
    LaunchedEffect(key1 = currentTime){
        if(currentTime!=0){
            delay(1000L)
            currentTime-=1
            return@LaunchedEffect
        }
        onFinish()
    }

    Box(modifier = Modifier.fillMaxSize()){
        Text(modifier = Modifier
            .align(Alignment.Center).background(Color.White),
            text = currentTime.toString())
    }




}
@Composable
private fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize()){
        CircularProgressIndicator(
            modifier = Modifier
                .size(70.dp)
                .align(Alignment.Center)
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GameMainScreen(
    modifier : Modifier = Modifier,
    nationalDex : Int
){
    val listState = rememberLazyListState()


    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        state = listState,
        reverseLayout = true
    ){
        item {
            Box(
                modifier = Modifier,
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = rememberImagePainter(
                        data =
                        when(nationalDex.toString().length){
                            1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00${nationalDex}.png"
                            2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0${nationalDex}.png"
                            else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${nationalDex}.png"
                        }
                    ),
                    contentDescription ="",
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.BottomStart)
                        .offset(y=-100.dp)
                )
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BlueAlpha80)
                        .border(width = 1.dp, color = Color.Black)
                        .padding(15.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    maxItemsInEachRow = 2
                ) {
                    ButtonSecondary(modifier = Modifier.widthIn(min = 130.dp), text = "Attack", isEnabled = true)
                    ButtonSecondary(modifier = Modifier.widthIn(min = 130.dp), text = "Hand", isEnabled = true)
                    ButtonSecondary(modifier = Modifier.widthIn(min = 130.dp), text = "Check", isEnabled = true)
                    ButtonSecondary(modifier = Modifier.widthIn(min = 130.dp), text = "Pass", isEnabled = true)



                }
            }


            Box(modifier = Modifier
                .fillMaxWidth()
                .height(200.dp))

            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    painter = rememberImagePainter(
                        data =
                        when((nationalDex+3).toString().length){
                            1 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/00${nationalDex+3}.png"
                            2 -> "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/0${nationalDex+3}.png"
                            else ->"https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/${nationalDex+3}.png"
                        }
                    ),
                    contentDescription ="",
                    modifier = Modifier
                        .size(250.dp)
                        .align(Alignment.TopEnd)

                )
            }

        }




    }










}