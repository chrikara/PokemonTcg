package com.example.pokemontcg

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokemontcg.presentation.features.deck.newdeck.CardList
import com.example.pokemontcg.presentation.features.deck.alldecks.AllDecksScreen
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
                    startDestination = Screen.Welcome.route
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
                    
                    composable(route = Screen.Deck.route){
                        CardList()
                    }

                }
            }
        }
    }
}
