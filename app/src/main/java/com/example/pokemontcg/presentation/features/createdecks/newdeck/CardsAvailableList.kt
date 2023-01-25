@file:OptIn(ExperimentalCoilApi::class)

package com.example.pokemontcg.presentation.features.createdecks.newdeck

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
import coil.annotation.ExperimentalCoilApi
import com.example.pokemontcg.presentation.features.createdecks.newdeck.components.CardListRow
import com.example.pokemontcg.presentation.features.createdecks.newdeck.components.DeckNumberHeader

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardList(
    viewModel: CardListViewModel = hiltViewModel()
) {
    val state = viewModel.state
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
                        println("$i row 1 card${state.cardList[i+1]} 2 card${state.cardList[i+i+1]}")
                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = state.cardList[i+i+1].imgString,
                            onClick1 = {viewModel.insertPokemonToDeck(state.cardList[i+i])},
                            onClick2 = {viewModel.insertPokemonToDeck(state.cardList[i+i+1])}

                        )
                    }else{
                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = "",
                            onClick1 = {viewModel.insertPokemonToDeck(state.cardList[i+1])},
                            onClick2 = {}
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


