package com.example.pokemontcg.presentation.features.game.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.pokemontcg.domain.model.defaults.DefaultPokedex
import com.example.pokemontcg.presentation.components.ButtonSecondary
import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.PokemonCard
import com.example.pokemontcg.ui.theme.GreenBrush
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.navigation.Screen

@Composable
fun GameHandBox(
    modifier : Modifier = Modifier,
    viewModel: GameViewModel,
    textButton1 : String,
    textButton2 : String,
    textButton3 : String = "End",
    onClick1 : (GameCard) -> Unit,
    onClick2: (GameCard) -> Unit,
    onClick3: () -> Unit = {},
    onItemClick : (GameCard) -> Unit = {},
    isButton3Visible : Boolean = false,
    selectedIndex : Int = 0,



) {


    val spacing = LocalSpacing.current
    val currentHand = viewModel.state.player.currentHand







    val listState = rememberLazyListState()

    Box(modifier = modifier.fillMaxSize()){

        Column(modifier = Modifier
            .padding(spacing.paddingExtraSmall)
            .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                state = listState
            ){
                items(currentHand){

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp)
                            .clickable {
                                onItemClick(it)
                            }


                            .clip(
                                shape = RoundedCornerShape(5.dp)
                            )

                            .border(
                                width = 3.dp,
                                brush = Brush.verticalGradient(GreenBrush),
                                shape = RoundedCornerShape(5.dp)
                            )
                            .background(
                                brush =
                                if (selectedIndex == currentHand.indexOf(it)
                                ) Brush.verticalGradient(
                                    GreenBrush
                                ) else Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        Color.Transparent
                                    )
                                )
                            )
                            .padding(15.dp)
                        ,
                        text = it.name,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.3f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 13.dp, start = 13.dp)
                        .weight(1f),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    val paddingValues = PaddingValues(horizontal = 10.dp, vertical = 25.dp)

                    if(isButton3Visible){
                        ButtonSecondary(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 50.dp), text = textButton3, isEnabled = true, paddingValues = paddingValues,
                            onClick = onClick3
                        )
                    }


                    if(currentHand.isNotEmpty()){
                        ButtonSecondary(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 25.dp), text = textButton1, isEnabled = true, paddingValues = paddingValues,
                            onClick = { onClick1(currentHand[selectedIndex]) }
                        )
                    }

                    if(currentHand.isNotEmpty()){
                        ButtonSecondary(modifier = Modifier
                            .fillMaxWidth(), text = textButton2, isEnabled = true, paddingValues = paddingValues,
                            onClick = {
                                println(currentHand.size)
                                onClick2(currentHand[selectedIndex])
                                println(currentHand.size)
//                                if(viewModel.state.currentState == GameState.GameSealedClass.CHOOSE_BENCH.HAND){
//                                    if(selectedIndex == currentHand.indexOf(currentHand.last()) ){
//                                        selectedIndex-=1
//                                    }
//                                }

                            }
                        )
                    }
                }
                if(currentHand.isNotEmpty()){
                    val selectedModifiedIndex = if(selectedIndex==currentHand.size) selectedIndex-1 else selectedIndex
                    GameCardInfoBox(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        imageUrl =


                        if(currentHand[selectedModifiedIndex] is PokemonCard){
                            val dex = DefaultPokedex.getKeyByPokemonName(DefaultPokedex.pokedexNationaltoNameHash, currentHand[selectedModifiedIndex].name)
                            "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/$dex.png"
                        }else{
                            currentHand[selectedModifiedIndex].image
                        }
                    )
                }
            }
        }
    }
}