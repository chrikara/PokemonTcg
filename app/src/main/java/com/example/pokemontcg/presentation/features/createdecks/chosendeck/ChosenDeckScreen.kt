package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.pokemontcg.domain.model.CardSaved
import com.example.pokemontcg.domain.model.DeckNumber
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.DeckNumberHeader
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.components.calcDominantColor
import com.example.pokemontcg.presentation.features.welcome.PrimaryButton
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.UiEvent

@OptIn(ExperimentalFoundationApi::class)
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

    val background = if(state.cardsSaved.isEmpty()) MaterialTheme.colorScheme.background else Color.DarkGray

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        verticalArrangement = Arrangement.SpaceBetween

    ) {

        DeckNumberHeader(
            modifier = Modifier.fillMaxWidth(),
            totalCards = state.cardsSaved.size,
            onFinishedRatio = { ratio ->
                viewModel.onEvent(ChosenDeckEvent.OnChangeGaugeRatio(ratio))
            },
            initialRatio = state.gaugeRatio,

        )

        if(state.cardsSaved.isEmpty()){
            TextForEmptyDeck(modifier = Modifier.weight(1f))
        }


        LazyRow(
            content = {
            items(
                items = state.cardsSaved,
                key = {it.id?: -1}
                )
                {cardSaved ->
                SavedImageInDeck(
                    modifier = Modifier.animateItemPlacement(),
                    cardSaved = cardSaved,
                    onClickImage = { viewModel.onEvent(ChosenDeckEvent.ShowCardInfo(cardSaved)) },
                    onClickInfo = { viewModel.onEvent(ChosenDeckEvent.ShowCardInfo(cardSaved)) },
                    onClickDelete = { viewModel.onEvent(ChosenDeckEvent.DeleteCardFromRoom(cardSaved)) }
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
    modifier : Modifier = Modifier,
    onClickImage: () -> Unit,
    onClickInfo: () -> Unit,
    onClickDelete: () -> Unit,
    cardSaved : CardSaved
){
    val defaultDominantColor =  Color.Transparent
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .shadow(
                elevation = 3.dp
            )
            .padding(3.dp)
            .clickable(
                onClick = onClickImage
            )
            .background(
                Brush.radialGradient(
                    listOf(
                        dominantColor,
                        Color.Transparent,
                    ),
                    radius = 1060f,
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cardSaved.pokemonImageUrl)
            .crossfade(true)
            .build(),
        onSuccess = {
            calcDominantColor(it.result.drawable) { color ->
                dominantColor = color

            }
        },
        contentDescription = "",
        modifier = Modifier
            .padding(top = spacing.paddingMegaLarge)
            .padding(bottom = spacing.paddingMedium)
            .size(285.dp)
            .clip(RoundedCornerShape(50.dp))

        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
            ,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Icon(
                imageVector = Icons.Rounded.Info,
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 70.dp)
                    .size(50.dp)
                    .alpha(0.8f)
                    .clip(CircleShape)
                    .clickable(onClick = onClickInfo)
                ,
                tint = MaterialTheme.colorScheme.onBackground
            )
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .alpha(0.8f)
                    .clip(CircleShape)
                    .clickable(
                        onClick = onClickDelete
                    )
                ,
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}


