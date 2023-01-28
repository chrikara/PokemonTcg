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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.CardItemToInsertToDeck
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.DeckNumberHeader
import com.example.pokemontcg.util.UiEvent
import com.example.pokemontcg.util.navigation.Screen

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ModifyDeckScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavController,
    viewModel: CardListViewModel = hiltViewModel(),
    deckNumber: Int
) {
    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.ShowSnackBar -> {
                    println("Before")
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        withDismissAction = true
                    )
                }
                else -> {}
            }
        }
    }

    val state = viewModel.state
    viewModel.getCardsForOneDeck(DeckNumber.fromInt(deckNumber))




    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    )
    {

        DeckNumberHeader(
            modifier = Modifier.fillMaxWidth(),
            totalCards = state.savedCardList.size,
            onFinishedRatio = { ratio ->
                viewModel.onChangeRatio(ratio)
            },
            initialRatio = state.gaugeRatio
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2)){

            items(state.cardList){ cardOverview ->

                CardItemToInsertToDeck(
                    image = cardOverview.imgString,
                    totalCounts = state.savedCardList.count{it.pokemonId == cardOverview.id },
                    onShowInfo = { navController.navigate(Screen.PokeCardInfo.route + "/${cardOverview.id}")},
                    onAddCard = { viewModel.insertPokemonToDeck(deckNumber , cardOverview, snackbarHostState) },
                    onDeleteCard = {viewModel.deletePokemonFromDeck(cardOverview)}
                )

            }
            }




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


