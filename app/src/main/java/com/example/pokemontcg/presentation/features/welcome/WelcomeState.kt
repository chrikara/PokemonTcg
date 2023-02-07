package com.example.pokemontcg.presentation.features.welcome

data class WelcomeState(
    val hasAlreadyGame : Boolean,
    val isDialogShown : Boolean = false
)