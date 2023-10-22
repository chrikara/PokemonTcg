package com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.use_cases

import com.example.pokemontcg.presentation.features.game.GameViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.check.GameCheckViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.GamePlayerTurnViewModel
import com.example.pokemontcg.presentation.features.game.presentation.subscreens.playerturn.PlayerTurnEvent

class ActionInHandUseCase {

    operator fun invoke(
        viewModelGame: GameViewModel,
        viewModelPlayerTurn : GamePlayerTurnViewModel,
        buttonText : String,

        ){
        when(buttonText){
            "Add Bench" -> {

            }
        }
    }
}
