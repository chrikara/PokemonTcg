package com.example.pokemontcg.presentation.features.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokemontcg.presentation.components.CustomDialog
import com.example.pokemontcg.presentation.components.PokemonTcgLogo
import com.example.pokemontcg.ui.theme.DarkDialog
import com.example.pokemontcg.ui.theme.LocalSpacing
import com.example.pokemontcg.util.navigation.Screen
import com.example.pokemontcg.util.UiEvent


@Composable
fun WelcomeScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: WelcomeViewModel = hiltViewModel()
)  {
    val spacing = LocalSpacing.current
    val state = viewModel.state

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        ,
    ){

        if(state.isDialogShown){
            CustomDialog(
                title = "Διαγραφή",
                backgroundColor = DarkDialog,
                text = "Αυτό θα διαγράψει όλα σου τα δεδομένα!",
                modifier = Modifier.height(200.dp),
                onDismiss = {
                    viewModel.onDialog()
                },
                onClickOk = {
                    onNavigate(UiEvent.Navigate(Screen.Main.route))
                    viewModel.onStartNewGame()
                    viewModel.deleteAllDataAndInsertDefaultOpponentsInDb()
                }
            )
        }



        PokemonTcgLogo(modifier = Modifier.align(TopCenter))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            PrimaryButton(
                text = "New Game",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = spacing.paddingLarge)

                ,
                onClick = {
                    if(state.hasAlreadyGame){
                        viewModel.onDialog()
                        return@PrimaryButton
                    }

                    onNavigate(UiEvent.Navigate(Screen.Main.route))
                    viewModel.onStartNewGame()
                    viewModel.deleteAllDataAndInsertDefaultOpponentsInDb()
                }
            )

            Spacer(modifier = Modifier.height(spacing.spaceMedium))

            PrimaryButton(
                text = "Continue",
                enabled = state.hasAlreadyGame,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = spacing.paddingLarge)
                ,
                onClick = {
                    onNavigate(UiEvent.Navigate(Screen.Main.route))
                }
            )
        }
    }
}
