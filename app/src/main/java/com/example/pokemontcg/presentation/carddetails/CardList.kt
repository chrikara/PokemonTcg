package com.example.pokemontcg.presentation.carddetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.pokemontcg.data.remote.api.dto.Data
import com.example.pokemontcg.presentation.carddetails.components.CardListRow

@OptIn(ExperimentalCoilApi::class)
@Composable
fun CardList(
    viewModel: CardListViewModel = hiltViewModel()
) {
    val state = viewModel.state

    Column(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.onBackground)){


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Image(
                    painter = rememberImagePainter(
                        data = "https://images.pokemontcg.io/base1/logo.png",
                        builder = {
                            crossfade(true)
                        }
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(150.dp)
                        .background(Color.Transparent)
                        .align(CenterHorizontally)
                        .weight(1f)
                )
            }
            items(state.cardList.size/2){ i ->
                if(state.cardList.size>= i + i +1){
                    CardListRow(
                        image1 = state.cardList[i + i].imgString ,
                        image2 = state.cardList[i+i+1].imgString
                    )
                }else{
                    println(i)
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


