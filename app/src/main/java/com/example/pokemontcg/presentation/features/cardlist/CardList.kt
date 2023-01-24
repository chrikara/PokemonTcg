@file:OptIn(ExperimentalCoilApi::class)

package com.example.pokemontcg.presentation.features.cardlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pokemontcg.presentation.features.cardlist.components.CardListRow

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardList(
    viewModel: CardListViewModel = hiltViewModel()
) {
    val state = viewModel.state
    println(state)



    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
    )
    {
        if(state.isLoading) CircularProgressIndicator(modifier = Modifier.size(150.dp).align(Center))
        if(state.error!="") ErrorText(text = state.error, modifier = Modifier.align(Center))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            content = {
                item {
                    PokemonLogo()
                }
                items(state.cardList.size/2){ i ->

                    if(state.cardList.size-1>= i + i +1){
                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = state.cardList[i+i+1].imgString
                        )
                    }else{
                        CardListRow(
                            image1 = state.cardList[i + i].imgString ,
                            image2 = ""
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
@Composable
private fun PokemonLogo(modifier: Modifier = Modifier){
    Image(
        painter = rememberImagePainter(
            data = "https://images.pokemontcg.io/base1/logo.png",
            builder = {
                crossfade(true)
            }
        ),
        contentDescription = "",
        modifier = modifier
            .size(150.dp)
            .background(MaterialTheme.colorScheme.surface)
           )

}


