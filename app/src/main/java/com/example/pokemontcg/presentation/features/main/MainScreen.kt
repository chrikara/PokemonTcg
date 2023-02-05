package com.example.pokemontcg.presentation.features.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokemontcg.R
import com.example.pokemontcg.domain.model.defaultGyms
import com.example.pokemontcg.presentation.features.createdecks.alldecks.components.PokeSprite
import com.example.pokemontcg.presentation.features.main.components.GymBox
import com.example.pokemontcg.presentation.features.main.components.InfoBox
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.UiEvent
import com.example.pokemontcg.util.navigation.Screen
import kotlinx.coroutines.flow.collect

@Composable
fun MainScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {

    val spacing = LocalSpacing.current

    LaunchedEffect(key1 = true){
        viewModel.uiEvent.collect{event ->
            when(event){
                is UiEvent.Navigate -> {
                    onNavigate(event)
                }


                else -> {}
            }
        }
    }


   
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(spacing.paddingMedium)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            item {
                Text(
                    text = "Οι τράπουλες σου",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                InfoBox(
                    title = "...Create your deck",
                    description = "You can create up to three decks!",
                    onClick =  viewModel::onNextAllDecksClick ,
                    imageUrl = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/4f7705ec-8c49-4eed-a56e-c21f3985254c/dah43cy-a8e121cb-934a-40f6-97c7-fa2d77130dd5.png/v1/fill/w_1024,h_1420,strp/pokemon_card_backside_in_high_resolution_by_atomicmonkeytcg_dah43cy-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQyMCIsInBhdGgiOiJcL2ZcLzRmNzcwNWVjLThjNDktNGVlZC1hNTZlLWMyMWYzOTg1MjU0Y1wvZGFoNDNjeS1hOGUxMjFjYi05MzRhLTQwZjYtOTdjNy1mYTJkNzcxMzBkZDUucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.9GzaYS7sd8RPY5FlHca09J9ZQZ9D9zI69Ru-BsbkLDA"
                )
                Spacer(modifier = Modifier.height(spacing.spaceMedium))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ){
                    PokeSprite(pokeResId = R.drawable.ivysaur, modifier = Modifier.size(100.dp))
                    PokeSprite(pokeResId = R.drawable.typhlosion, modifier = Modifier.size(100.dp))
                }
                Spacer(modifier = Modifier.height(spacing.spaceMegaMegaLarge))

                Text(
                    text = "Γυμναστήρια",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp,
                )

                Spacer(modifier = Modifier.height(spacing.spaceMedium))
            }
            items(defaultGyms){gym ->
                GymBox(
                    onClick = viewModel::onNextGymClick,
                    gymValue = gym.value,
                    gymName = gym.name ,
                    isEnabled = gym.enabled,
                    leaderUrl = gym.leaderUrl,
                    isCurrent = gym.isCurrent
                )
                Spacer(modifier = Modifier.height(14.dp))
            }
            item{

                Spacer(modifier = Modifier.height(19.dp))

            Text(
                text = "Έρχονται περισσότερα γυμναστήρια και κάρτες...",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp,
                textAlign = TextAlign.Center
            )
                Spacer(modifier = Modifier.height(19.dp))

            }
        }
    )

}