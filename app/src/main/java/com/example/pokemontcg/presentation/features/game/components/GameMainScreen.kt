package com.example.pokemontcg.presentation.features.game.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.GameEvent
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.ui.theme.BlueAlpha80

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameMainScreen(
    modifier : Modifier = Modifier,
    viewModel: GameViewModel
){
    val state = viewModel.state
//    val dex = DefaultPokedex.getKeyByPokemonName(DefaultPokedex.pokedexNationaltoNameHash, selectedPokemonCardImage.name)
//    "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dex.png"

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.EXPLANATION){
        GameActionWindow(title = " PLAYER \n TURN ", message ="It's your turn now!\n\n") {
            viewModel.onEvent(GameEvent.OnChangeGameState(GameState.GameSealedClass.PLAYER_TURN.MAIN))
        }
    }

    if(state.currentState == GameState.GameSealedClass.PLAYER_TURN.MAIN){
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            reverseLayout = true
        ){
            item {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.Center
                ){

                    Image(
                        painter = rememberImagePainter(
                            data = DefaultPokedex.imageUrlFromDex(state.player.currentPokemon!!.nationalDex)
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
                            data = DefaultPokedex.imageUrlFromDex(state.opponent.currentPokemon!!.nationalDex)
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


}