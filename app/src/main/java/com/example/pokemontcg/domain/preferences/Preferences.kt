package com.example.pokemontcg.domain.preferences

interface Preferences {

    fun saveHasAlreadyGame(hasAlreadyGame: Boolean)
    fun loadHasAldreadGame() : Boolean
}