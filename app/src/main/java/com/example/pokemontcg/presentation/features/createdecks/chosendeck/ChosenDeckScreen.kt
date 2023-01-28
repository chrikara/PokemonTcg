package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.DeckNumberHeader
import com.example.pokemontcg.presentation.features.welcome.PrimaryButton
import com.example.pokemontcg.util.UiEvent

@Composable
fun ChosenDeckScreen(
    deckNumber: Int,
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: ChosenDeckViewModel = hiltViewModel()
) {


    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.Navigate ->{
                    onNavigate(event)
                }

                else -> Unit
            }
        }
    }

    val deckChosen = DeckNumber.fromInt(deckNumber)
    viewModel.onEvent(ChosenDeckEvent.GetAllCardsFromRoom(deckNumber = deckChosen))

    val state = viewModel.state
    println(state.gaugeRatio)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween

    ) {

        DeckNumberHeader(
            modifier = Modifier.fillMaxWidth(),
            totalCards = state.cardsSaved.size,
            onFinishedRatio = { ratio ->
                viewModel.onEvent(ChosenDeckEvent.OnChangeGaugeRatio(ratio))
            },
            initialRatio = state.gaugeRatio
        )

        if(state.cardsSaved.isEmpty()){
            TextForEmptyDeck(modifier = Modifier.weight(1f))
        }


        LazyRow(
        content = {
            items(state.cardsSaved){cardSaved ->
                SavedImageInDeck(
                    viewModel = viewModel,
                    cardSaved = cardSaved
                )
            }
        }
    )


        PrimaryButton(
            modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, bottom = 15.dp)
                .fillMaxWidth(),
            text = "Modify Deck",
            textAlign = TextAlign.Center,
            onClick = {
                viewModel.onEvent(ChosenDeckEvent.OnModifyDeckClick(deckNumber))
            }
        )
    }



    }



@Composable
private fun TextForEmptyDeck(modifier : Modifier = Modifier){
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("Η τράπουλα σου είναι άδεια. Πάτα στο")
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(" Modify Deck ")
                }
                withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onBackground)) {
                    append("για να προσθέσεις κάρτες!")
                }
            },
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun SavedImageInDeck(
    viewModel: ChosenDeckViewModel,
    cardSaved : CardSaved
){
    Box(
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = rememberImagePainter(
                data = cardSaved.pokemonImageUrl,
            ),
            modifier = Modifier
                .size(350.dp)
                .clickable(
                    onClick = {
                        viewModel.onEvent(ChosenDeckEvent.ShowCardInfo(cardSaved))
                    }
                ),
            contentDescription ="",

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
        ){
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 40.dp)
                    .size(35.dp)
                    .alpha(0.7f)
                    .clickable(onClick = {
                        viewModel.onEvent(ChosenDeckEvent.ShowCardInfo(cardSaved))
                    })
                ,
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .alpha(0.7f)
                    .clickable(
                        onClick = {
                            viewModel.onEvent(ChosenDeckEvent.DeleteCardFromRoom(cardSaved))
                        }
                    )
                ,
                tint = MaterialTheme.colorScheme.primary
            )
        }

    }

}


