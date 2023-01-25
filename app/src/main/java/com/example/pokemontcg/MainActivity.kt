package com.example.pokemontcg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemontcg.presentation.features.createdecks.chosendeck.ChosenDeck
import com.example.pokemontcg.presentation.features.createdecks.newdeck.CardList
import com.example.pokemontcg.presentation.features.createdecks.alldecks.AllDecksScreen
import com.example.pokemontcg.presentation.features.main.MainScreen
import com.example.pokemontcg.presentation.features.welcome.WelcomeScreen
import com.example.pokemontcg.ui.theme.PokemonTcgTheme
import com.example.pokemontcg.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokemonTcgTheme {

                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = Screen.AllDecks.route
                ){
                    composable(route = Screen.Welcome.route){
                        WelcomeScreen(navController = navController)
                    }

                    composable(route = Screen.Main.route){
                        MainScreen(navController = navController)
                    }

                    composable(route = Screen.AllDecks.route){
                        AllDecksScreen(navController = navController)
                    }
                    composable(route = Screen.ChosenDeck.route){
                        ChosenDeck(navController = navController)
                    }
                    composable(route = Screen.DeckModify.route){
                        CardList()
                    }

                }
            }
        }
    }
}
