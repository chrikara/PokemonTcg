package com.example.pokemontcg.util

sealed class UiEvent {

    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: String) : UiEvent()


}
