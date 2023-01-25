package com.example.pokemontcg.presentation.features.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pokemontcg.R
import com.example.pokemontcg.presentation.components.PokemonTcgLogo
import com.example.pokemontcg.util.Screen


@Composable
fun WelcomeScreen(
    navController: NavController
) {

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        ,
    ){

        PokemonTcgLogo(modifier = Modifier.align(TopCenter))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            PrimaryButton(
                text = "New Game",
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = 25.dp)
                ,
                onClick = { navController.navigate(Screen.Main.route) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            PrimaryButton(
                text = "Continue",
                enabled = false,
                modifier = Modifier
                    .align(CenterHorizontally)
                    .padding(horizontal = 25.dp)
                ,
                onClick = {}
            )
        }
    }
}