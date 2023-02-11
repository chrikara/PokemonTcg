package com.example.pokemontcg.presentation.features.game.presentation.subscreens.gameplayerturn

import android.content.pm.PackageManager.ComponentEnabledSetting

data class GamePlayerTurnState(
    val isAttackButtonEnabled : Boolean = false,
    val cardInfoImage : String = ""

)