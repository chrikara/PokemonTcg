package com.example.pokemontcg

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pokemontcg.presentation.features.createdecks.chosendeck.ChosenDeckScreen
import com.example.pokemontcg.presentation.features.createdecks.modifydeck.ModifyDeckScreen
import com.example.pokemontcg.presentation.features.createdecks.alldecks.AllDecksScreen
import com.example.pokemontcg.presentation.features.game.GameScreen
import com.example.pokemontcg.presentation.features.gyms.GymScreen
import com.example.pokemontcg.presentation.features.main.MainScreen
import com.example.pokemontcg.presentation.features.pokecardinfo.PokeCardInfoScreen
import com.example.pokemontcg.presentation.features.welcome.WelcomeScreen
import com.example.pokemontcg.ui.theme.PokemonTcgTheme
import com.example.pokemontcg.util.navigation.Screen
import com.example.pokemontcg.util.navigation.navigate
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PokemonTcgTheme {

                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) {


                    NavHost(
                        navController = navController,
                        startDestination = Screen.Game.route +"/{deckNumber}/{opponent}"
                    ){

                        composable(route = Screen.Welcome.route){
                            WelcomeScreen(onNavigate = navController::navigate)

                        }

                        composable(route = Screen.Main.route){
                                MainScreen(onNavigate = { event ->
                                    navController.navigate(event)
                                })
                            }

                        composable(route = Screen.AllDecks.route){
                            AllDecksScreen(navController = navController)
                        }
                        composable(
                            route = Screen.ChosenDeck.route + "/{deckNumber}",
                            arguments = listOf(
                                navArgument("deckNumber"){
                                    type = NavType.IntType
                                }
                            )
                        ){
                            val deckNumber = it.arguments?.getInt("deckNumber") ?: 1
                            ChosenDeckScreen(
                                deckNumber = deckNumber,
                                onNavigate = navController::navigate
                            )
                        }
                        composable(route = Screen.ModifyDeck.route + "/{deckNumber}",
                            arguments = listOf(
                                navArgument("deckNumber"){
                                    type = NavType.IntType
                                }
                            )
                            ){
                            val deckNumber = it.arguments?.getInt("deckNumber") ?: 1
                            ModifyDeckScreen(
                                snackbarHostState = snackbarHostState,
                                navController = navController,
                                deckNumber = deckNumber
                            )
                        }

                        composable(route = Screen.PokeCardInfo.route + "/{pokeId}",
                            arguments = listOf(
                                navArgument("pokeId"){
                                    type = NavType.StringType
                                }
                            )
                        ){
                            PokeCardInfoScreen(navController = navController)
                        }

                        composable(route = Screen.Gym.route + "/{gymName}",
                        arguments = listOf(
                            navArgument("gymName"){
                                type = NavType.StringType
                            }
                        )
                        ){
                            GymScreen(
                                onNavigate = navController::navigate
                            )
                        }
                        composable(route = Screen.Game.route +"/{deckNumber}/{opponent}",
                        arguments = listOf(
                            navArgument("deckNumber"){
                                type = NavType.StringType
                            },
                            navArgument("opponent"){
                                type = NavType.StringType
                            }
                        )
                        ){
                            GameScreen()
                        }
                    }
                }
            }
        }
    }
}
