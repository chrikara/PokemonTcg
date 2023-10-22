package com.example.pokemontcg.presentation.features.game.presentation.subscreens.choosebench

import com.example.pokemontcg.presentation.features.game.GameState
import com.example.pokemontcg.presentation.features.game.domain.model.GameCard
import com.example.pokemontcg.presentation.features.game.domain.model.Player
import com.example.pokemontcg.util.navigation.Screen

sealed class GameChooseBenchEvent() {


    data class OnItemSelected(val index : Int) : GameChooseBenchEvent()

    data class OnCardInfoImage(val imageUrl : String) : GameChooseBenchEvent()
}