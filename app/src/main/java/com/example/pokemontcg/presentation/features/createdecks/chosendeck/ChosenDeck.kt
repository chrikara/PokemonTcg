package com.example.pokemontcg.presentation.features.createdecks.chosendeck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemontcg.presentation.features.createdecks.newdeck.components.DeckNumberHeader
import com.example.pokemontcg.presentation.features.welcome.PrimaryButton
import com.example.pokemontcg.util.Screen

@Composable
fun ChosenDeck(
    navController: NavController
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),
        ){
    Column(modifier = Modifier
        .fillMaxSize()
    )
    {

        DeckNumberHeader(
            modifier = Modifier.fillMaxWidth(),
            totalCards = 45
        )
    }
        PrimaryButton(
            modifier = Modifier
                .padding(horizontal = 15.dp, vertical = 15.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
            ,
            text = "Modify Deck",
            textAlign = TextAlign.Center,
            onClick = {
                navController.navigate(Screen.DeckModify.route)
            }
        )
    }
}