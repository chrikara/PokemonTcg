package com.example.pokemontcg.data.preferences

import android.content.SharedPreferences
import com.example.pokemontcg.domain.preferences.Preferences

class DefaultPreferences (
    private val sharedPref : SharedPreferences
        ): Preferences {
    override fun saveHasAlreadyGame(hasAlreadyGame: Boolean) {
        sharedPref.edit()
            .putBoolean(KEY_HASALREADYGAME, hasAlreadyGame)
            .apply()
    }

    override fun loadHasAldreadGame(): Boolean {
        return sharedPref.getBoolean(KEY_HASALREADYGAME, false)
    }

    companion object{
        val KEY_HASALREADYGAME = "has_already_game"
    }
}