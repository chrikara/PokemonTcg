package com.example.pokemontcg.util.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.pokemontcg.util.UiEvent


fun NavController.navigate(event : UiEvent.Navigate) {   // This is an extension for the NavController that just uses the navigate function
                                                         // but passing a UiEvent as a parameter and not a just route
    this.navigate(event.route)

}