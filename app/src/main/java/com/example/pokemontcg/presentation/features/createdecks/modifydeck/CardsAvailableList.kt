@file:OptIn(ExperimentalCoilApi::class)

package com.example.pokemontcg.presentation.features.createdecks.modifydeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.CardListRow
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.DeckNumberHeader
import com.example.pokemontcg.util.Screen

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardList(
    navController: NavController,
    viewModel: CardListViewModel = hiltViewModel(),
    deckNumber: Int
) {
    val state = viewModel.state
    viewModel.getCardsForOneDeck(DeckNumber.fromInt(deckNumber))

    println(state)


    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    )
    {


        DeckNumberHeader(
            modifier = Modifier.fillMaxWidth(),
            totalCards = state.savedCardList.size
        )

        if(state.isLoading) {
            Spacer(modifier = Modifier.height(150.dp))
            CircularProgressIndicator(modifier = Modifier
                .size(150.dp)
                .align(CenterHorizontally))
        }
        if(state.error!="") {
            Spacer(modifier = Modifier.height(150.dp))
            ErrorText(text = state.error)
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            content = {

                items(state.cardList.size/2){ i ->

                    if(state.cardList.size-1>= i + i +1){

                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = state.cardList[i+i+1].imgString,
                            onClickAdd1st = {viewModel.insertPokemonToDeck(deckNumber, state.cardList[i+i])},
                            onClickAdd2nd = {viewModel.insertPokemonToDeck(deckNumber,state.cardList[i+i+1])},
                            onClickSub1st = {
                                            println(state.cardList[i+i])
                                viewModel.deletePokemonFromDeck(state.cardList[i+i])
                                            },
                            onClickSub2nd = {
                                viewModel.deletePokemonFromDeck(state.cardList[i+i+1])
                            },
                            totalCounts1 = state.savedCardList.count { it.pokemonId == state.cardList[i+i].id },
                            totalCounts2 = state.savedCardList.count { it.pokemonId == state.cardList[i+i+1].id },
                            onClickCardInfo1st = {navController.navigate(Screen.PokeCardInfo.route + "/${state.cardList[i+i].id}")},
                            onClickCardInfo2nd= {navController.navigate(Screen.PokeCardInfo.route + "/${state.cardList[i+i+1].id}")},

                        )
                    }else{
                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = "",
                            onClickAdd1st = {viewModel.insertPokemonToDeck(deckNumber,state.cardList[i+1])},
                            onClickAdd2nd = {},
                            onClickSub1st = {
                                viewModel.deletePokemonFromDeck(state.cardList[i+i])
                            },
                            onClickSub2nd = {
                                viewModel.deletePokemonFromDeck(state.cardList[i+i+1])
                            },
                            totalCounts1 = state.savedCardList.count { it.pokemonId == state.cardList[i+i].id },
                            totalCounts2 = state.savedCardList.count { it.pokemonId == state.cardList[i+i+1].id },
                            onClickCardInfo1st = {navController.navigate(Screen.PokeCardInfo.route + "/${state.cardList[i+i].id}")},
                            onClickCardInfo2nd= {},
                        )
                    }
                }
            }
        )
    }
}

@Composable
private fun ErrorText(text : String, modifier: Modifier = Modifier){
    Text(
        text = text,
        modifier = modifier.padding(25.dp),
        color = MaterialTheme.colorScheme.onBackground,
        textAlign = TextAlign.Center
    )
}


