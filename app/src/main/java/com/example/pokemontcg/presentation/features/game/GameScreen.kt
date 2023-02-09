@file:OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)

package com.example.pokemontcg.presentation.features.game

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex.pokedexNationaltoNameHash
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.CardItemToInsertToDeck
import com.example.pokemontcg.presentation.features.game.components.GameActionWindow
import com.example.pokemontcg.presentation.features.game.components.GameCardInHandBox
import com.example.pokemontcg.presentation.features.game.components.GameHandBox
import com.example.pokemontcg.presentation.features.game.domain.model.EnergyCard
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.presentation.features.pokecardinfo.components.PokeCardInfo
import com.example.pokemontcg.ui.theme.BlueAlpha80
import com.example.pokemontcg.ui.theme.GreenBrush
import com.example.pokemontcg.ui.theme.GreenButtonColor
import com.example.pokemontcg.ui.theme.LocalSpacing
import kotlinx.coroutines.delay

@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel()
) {

    val state = viewModel.state

    if(state.currentState == GameState.GameSealedClass.LOADING){
        LoadingScreen()
    }

    if(state.currentState == GameState.GameSealedClass.START){
        ShuffleDeckGame(secondsToStart = 1){
            viewModel.onEvent(GameEvent.OnShuffleDeck(state.player))
            viewModel.onEvent(GameEvent.OnShuffleDeck(state.opponent))
            viewModel.onEvent(GameEvent.OnGive7CardsToEachPlayer)
        }
    }

    if(
        state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.EXPLANATION) ||
        state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.HAND) ||
        state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.CARD_INFO)
    ){
        GameChooseActive(
            viewModel = viewModel,
            gameCards = state.player.currentHand
        )
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN){
        GameMainScreen(nationalDex = 2)
    }





}

@Composable
fun GameChooseActive(
    viewModel: GameViewModel,
    gameCards : List<GameCard>
) {
    val spacing = LocalSpacing.current

    var isBackIntercepted by remember{
        mutableStateOf(true)
    }
    val state = viewModel.state
    var imageUrl by remember {
        mutableStateOf("")
    }

//    val fakePokemonList = mutableListOf<GameCard>(
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//        PokemonCard(baseId = "base1-1", name = "Alakazam", image = "https://images.pokemontcg.io/base1/1_hires.png"),
//
//    )

    BackHandler(enabled = isBackIntercepted) {
        if(state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.CARD_INFO))
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.HAND)))
    }

    if(state.currentState == GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.EXPLANATION)){
        GameActionWindow(
            title = "ACTIVE\nPOKEMON",
            message = "Choose a basic Pokemon card as your active!",
            onClick = {
                viewModel.onEvent(GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.HAND)
                ))
            }
        )
    }
    if(state.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.HAND)){
        GameHandBox(
            gameCards = state.player.currentHand,
            onClick1 = {
                viewModel.onEvent(GameEvent.OnChangeGameState(
                    GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.CARD_INFO))
                )
                imageUrl = it.image
                       },
            onClick2 = {}
        )
    }

    if(state.currentState ==  GameState.GameSealedClass.CHOOSE_ACTIVE(GameState.CHOOSE_ACTIVE_STATE.CARD_INFO)){
        GameCardInHandBox(
            imageUrl = imageUrl,
            radius = 1500f
        )
    }

    }

@Composable
fun ShuffleDeckGame(secondsToStart : Int, onFinish : () -> Unit) {
    var currentTime by remember {
        mutableStateOf(secondsToStart)
    }
    LaunchedEffect(key1 = true){
        while(currentTime!=0){
            delay(1000L)
            currentTime-=1
        }
        onFinish()
    }

    Box(modifier = Modifier.fillMaxSize()){
        Text(modifier = Modifier
            .align(Alignment.Center),
            text = "Εδώ θα βάλω animation με shuffle\n${currentTime.toString()}",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
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
                        .offset(y = -100.dp)
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
                    val paddingValues = PaddingValues(15.dp)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Attack", paddingValues = paddingValues, isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Hand", paddingValues = paddingValues,isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Check", paddingValues = paddingValues,isEnabled = true)
                    ButtonSecondary(modifier = Modifier.weight(1f), text = "Pass", paddingValues = paddingValues,isEnabled = true)

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
                        .size(200.dp)
                        .align(Alignment.TopEnd)

                )
            }

        }




    }










}